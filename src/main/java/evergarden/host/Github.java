/*
 * Copyright (C) 2025 The EVERGARDEN Development Team
 *
 * Licensed under the MIT License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          https://opensource.org/licenses/MIT
 */
package evergarden.host;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import evergarden.Document;
import evergarden.Region;
import evergarden.javadoc.Markdown;
import kiss.I;
import kiss.JSON;
import kiss.Variable;
import kiss.XML;

class Github implements Hosting {

    private final REST rest = I.make(REST.class);

    private final String owner;

    private final String name;

    private final String branch;

    private LocalDate published;

    private List<Contributor> contributors;

    private List<Release> releases;

    Github(URI uri) {
        String path = uri.getPath();
        int index = path.indexOf('/', 1);
        this.owner = path.substring(1, index);
        this.name = path.substring(index + 1);

        JSON json = I.json("https://api.github.com/repos" + path);
        this.branch = json.get(String.class, "default_branch");
    }

    private synchronized JSON metadata() {
        return rest.data("https://api.github.com/repos/" + owner + "/" + name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String owner() {
        return owner;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String language() {
        return metadata().text("language");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countFork() {
        return metadata().get(int.class, "forks_count");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countStar() {
        return metadata().get(int.class, "stargazers_count");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countWatch() {
        return metadata().get(int.class, "watchers_count");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int countIssue() {
        return metadata().get(int.class, "open_issues_count");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String icon() {
        return metadata().get("owner").text("avatar_url");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String id() {
        return owner + "/" + name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String description() {
        return Variable.of(metadata().text("description")).or("");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String license() {
        return metadata().get("license").text("spdx_id");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized List<Contributor> contributors() {
        if (contributors == null) {
            contributors = new ArrayList();

            rest.data(metadata().text("contributors_url")).find("*").forEach(json -> {
                contributors.add(new Contributor(json.text("login"), json.text("avatar_url"), json.text("html_url")));
            });
        }
        return contributors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Release> releases() {
        if (releases == null) {
            releases = new ArrayList();

            rest.data("https://api.github.com/repos/" + owner + "/" + name + "/releases").find("*").forEach(json -> {
                releases.add(new Release(json.text("tag_name"), Instant.parse(json.text("published_at"))
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(), Markdown.parse(json.text("body")), json.text("html_url")));
            });
        }
        return releases;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String service() {
        return "GitHub";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String location() {
        return "https://github.com/" + owner + "/" + name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateCommunity() {
        return location() + "/discussions";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateChangeLog() {
        return "https://raw.githubusercontent.com/" + owner + "/" + name + "/" + branch + "/CHANGELOG.md";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateReadme() {
        return "https://raw.githubusercontent.com/" + owner + "/" + name + "/" + branch + "/README.md";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateIssues() {
        return location() + "/issues";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateNewIssue(String title, String label, String body) {
        return location() + "/issues/new?title=" + title + "&labels=" + label + "&body=" + URLEncoder.encode(body, StandardCharsets.UTF_8);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateReader(Region region) {
        return location() + "/blob/" + branch + "/src/test/java/" + region.location() + "#L" + region.startLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String locateEditor(Region region) {
        return location() + "/edit/" + branch + "/src/test/java/" + region.location() + "#L" + region.startLine() + "-L" + region.endLine();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Document getChangeLog(String text) {
        // Parse markdown and convert to structured HTML
        Node root = Parser.builder().build().parse(text);
        String html = HtmlRenderer.builder().escapeHtml(true).build().render(root);

        // convert h3 with bugfix to h2
        html = html.replaceAll("<h3>(.+\\(.+)</h3>", "<h2>$1</h2>");

        // structure flat HTML
        StringBuilder b = new StringBuilder();
        int[] ranks = {0, 0, 0, 0, 0, 0};
        Matcher matcher = Pattern.compile("<h(\\d)>").matcher(html);
        while (matcher.find()) {
            int rank = Integer.parseInt(matcher.group(1));
            int count = ranks[rank]++;
            int nest = 0;

            // reset lower rank's count
            for (int i = rank + 1; i < ranks.length; i++) {
                if (ranks[i] != 0) {
                    nest++;
                    ranks[i] = 0;
                }
            }

            matcher.appendReplacement(b, count == 0 ? "<section><h" + rank + ">"
                    : "</section>".repeat(nest + 1) + "<section><h" + rank + ">");
        }
        matcher.appendTail(b);
        b.append("</section></section></section>");

        return new ChangeLog(I.xml(b.toString()), 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized LocalDate getLatestPublishedDate() {
        if (published == null) {
            published = I.http("https://github.com/" + owner + "/" + name + "/releases/latest", XML.class).waitForTerminate().map(html -> {
                String text = html.find(".markdown-body h2").first().text();
                int start = text.indexOf('(');
                int end = text.lastIndexOf(')');
                return LocalDate.parse(text.substring(start + 1, end), DateTimeFormatter.ISO_LOCAL_DATE);
            }).to().or(LocalDate.now());
        }
        return published;
    }

    /**
     * Document for change log.
     */
    private class ChangeLog implements Document {

        private final List<Document> children = new ArrayList();

        private final String title;

        private final int nest;

        private final XML doc;

        /**
         * Build the document for change log from markdown in Github.
         */
        private ChangeLog(XML xml, int nest) {
            this.nest = nest;
            this.title = xml.find(">h" + nest).text();
            this.doc = xml.find(">ul)");
            xml.find(">section").forEach(sec -> {
                children.add(new ChangeLog(sec, nest + 1));
            });

            if (nest == 2) {
                children.add(new Asset(title.substring(0, title.indexOf(" "))));
            }
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String id() {
            return title.replaceAll("\\s", "");
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String title() {
            return title;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean hasContents() {
            return 2 <= nest;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public XML contents() {
            return doc;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public List<Document> children() {
            return children;
        }
    }

    /**
     * 
     */
    private class Asset implements Document {

        private final String version;

        private Asset(String version) {
            this.version = version;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String id() {
            return "Assets";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String title() {
            return "Assets";
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public XML contents() {
            return I.xml("<ul><li><a href=\"https://github.com/" + owner + "/" + name + "/archive/refs/tags/" + version + ".zip\">Source code</a> (zip)</li></ul>");
        }
    }
}