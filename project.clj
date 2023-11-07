(defproject io.logicblocks/halboy "6.0.1-RC3"
  :description "a hypermedia parser and navigator"
  :license {:name "MIT License"
            :url  "https://opensource.org/licenses/MIT"}
  :url "https://github.com/logicblocks/halboy"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/core.cache "1.0.225"]
                 [org.clojure/core.rrb-vector "0.1.2"]

                 [http-kit "2.6.0"]
                 [clj-http "3.12.3"]

                 [cheshire "5.11.0"]
                 [medley "1.4.0"]
                 [uritemplate-clj "1.3.1"]
                 [org.bovinegenius/exploding-fish "0.3.6"]]
  :plugins [[lein-cloverage "1.2.4"]
            [lein-shell "0.5.0"]
            [lein-ancient "0.7.0"]
            [lein-changelog "0.3.2"]
            [lein-cprint "1.3.3"]
            [lein-eftest "0.6.0"]
            [lein-codox "0.10.8"]
            [lein-cljfmt "0.9.2"]
            [lein-kibit "0.1.8"]
            [lein-bikeshed "0.5.2"]
            [jonase/eastwood "1.4.0"]]
  :profiles {:shared
             {:dependencies [[nrepl "1.0.0"]
                             [http-kit.fake "0.2.2"]
                             [clj-http-fake "1.0.4"]
                             [eftest "0.6.0"]]}

             :test
             [:shared {:eftest {:multithread? false}}]

             :dev
             [:shared {:source-paths ["dev"]
                       :eftest       {:multithread? false}}]

             :prerelease
             {:release-tasks
              [["shell" "git" "diff" "--exit-code"]
               ["change" "version" "leiningen.release/bump-version" "rc"]
               ["change" "version" "leiningen.release/bump-version" "release"]
               ["vcs" "commit" "Pre-release version %s [skip ci]"]
               ["vcs" "tag"]
               ["deploy"]]}

             :release
             {:release-tasks
              [["shell" "git" "diff" "--exit-code"]
               ["change" "version" "leiningen.release/bump-version" "release"]
               ["codox"]
               ["changelog" "release"]
               ["shell" "sed" "-E" "-i.bak" "s/\"[0-9]+\\.[0-9]+\\.[0-9]+\"/\"${:version}\"/g" "README.md"]
               ["shell" "rm" "-f" "README.md.bak"]
               ["shell" "git" "add" "."]
               ["vcs" "commit" "Release version %s [skip ci]"]
               ["vcs" "tag"]
               ["deploy"]
               ["change" "version" "leiningen.release/bump-version" "patch"]
               ["change" "version" "leiningen.release/bump-version" "rc"]
               ["change" "version" "leiningen.release/bump-version" "release"]
               ["vcs" "commit" "Pre-release version %s [skip ci]"]
               ["vcs" "tag"]
               ["vcs" "push"]]}}
  :eftest {:multithread? false}

  :target-path "target/%s/"

  :cloverage
  {:ns-exclude-regex [#"^user"]}

  :codox
  {:namespaces  [#"^halboy\."]
   :metadata    {:doc/format :markdown}
   :output-path "docs"
   :doc-paths   ["docs"]
   :source-uri  "https://github.com/logicblocks/halboy/blob/{version}/{filepath}#L{line}"}

  :cljfmt {:indents ^:replace {#".*" [[:inner 0]]}}

  :eastwood {:config-files ["config/linter.clj"]}

  :bikeshed {:name-collisions false
             :long-lines      false}

  :deploy-repositories
  {"releases"  {:url "https://repo.clojars.org" :creds :gpg}
   "snapshots" {:url "https://repo.clojars.org" :creds :gpg}})
