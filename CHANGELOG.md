# Change Log

All notable changes to this project will be documented in this file. This
change log follows the conventions of
[keepachangelog.com](http://keepachangelog.com/).

## [Unreleased]

## [6.0.0] — 2023-06-09

### Changed

- This library was forked from `jimmythompson/halboy`
- The `clj-http` base HTTP client is now the default HTTP client.
- The `halboy.navigator/discover` function now allows query params to be
  provided via a new three argument signature.
- All dependencies have been updated to their latest versions.

### Added

- Navigator instances can now focus on embedded resources allowing navigation to
  be continued in the context of the embedded resource, via the
  `halboy.navigator/focus` function.

## [5.1.2] — 2023-01-01

### Changed

- If a navigation response indicates a redirect but there is no URL in a
  Location header, a more descriptive error is now thrown.

### Added

- A new HTTP client based on `clj-http` has now been introduced, the default
  HTTP client based on `http-kit` has been cloned to `HttpKitHttpClient`.

### Deprecated

- As part of the introduction of `clj-http`, the default HTTP client has now
  been deprecated pending a move to the `clj-http` client as the default in
  version 6.0.0.

## [5.1.1] — 2020-11-20

### Fixed

- `halboy.resource/get-href` now correctly handles vectors of hrefs.

## [5.1.0] — 2019-04-29

### Changed

- If a non-JSON response body is received after a navigation, a more descriptive
  error is now thrown.

## [5.0.0] — 2019-02-21

### Changed

- Resources no longer add links, embedded resources or properties when their
  value is `nil`.

## [4.0.0] — 2018-08-12

### Changed

- Prior to this version, the query parameter map passed to the underlying HTTP
  client received a map with `String` keys and values of the type passed to the
  `navigator` function. In this release, all values are converted to `String`s
  or lists of `String`s to better support URI templating. If tests of code that
  use halboy make use of `http-kit.fake` they may need to be updated to match
  the new query parameter format.
- `navigator` now fully supports URI templates as defined in
  [RFC6570](https://tools.ietf.org/html/rfc6570) (level 4).

## [3.1.2] - 2018-08-09

### Added

- `navigator` now allows multiple templated query parameters in templated hrefs.

## [3.1.1] - 2018-08-02

### Added

- `navigator` now supports HTTP HEAD requests.

## [3.1.0] - 2018-06-11

### Added

- You can now pass a `String` when creating a resource and the full self link
  will be created for you.
- You can now create resources with multiple hrefs for the same link name.

## [3.0.0] - 2018-02-14

### Added

- You can now specify your own JSON HTTP client.
- You can now pass global request options through to the embedded HTTP client.

### Changed

- `resource/get-links` has been renamed to `resource/links`.
- `navigator/options` has been renamed to `navigator/settings`.

[3.0.0]: https://github.com/logicblocks/halboy/compare/3.0.0...3.0.0

[3.1.0]: https://github.com/logicblocks/halboy/compare/3.0.0...3.1.0

[3.1.1]: https://github.com/logicblocks/halboy/compare/3.1.0...3.1.1

[3.1.2]: https://github.com/logicblocks/halboy/compare/3.1.1...3.1.2

[4.0.0]: https://github.com/logicblocks/halboy/compare/3.1.2...4.0.0

[5.0.0]: https://github.com/logicblocks/halboy/compare/4.0.0...5.0.0

[5.1.0]: https://github.com/logicblocks/halboy/compare/5.0.0...5.1.0

[5.1.1]: https://github.com/logicblocks/halboy/compare/5.1.0...5.1.1

[5.1.2]: https://github.com/logicblocks/halboy/compare/5.1.1...5.1.2

[6.0.0]: https://github.com/logicblocks/halboy/compare/5.1.2...6.0.0
[Unreleased]: https://github.com/logicblocks/halboy/compare/6.0.0...HEAD
