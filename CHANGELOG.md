## 6.0.0 (June 8th, 2023)

CHANGES:

* This library was forked from `jimmythompson/halboy`

IMPROVEMENTS:

* The `clj-http` base HTTP client is now the default HTTP client.
* The `halboy.navigator/discover` function now allows query params to be
  provided via a new three argument signature.
* All dependencies have been updated to their latest versions.
* Navigator instances can now focus on embedded resources allowing navigation to
  be continued in the context of the embedded resource, via the
  `halboy.navigator/focus` function.

## 5.1.2 (June 15th, 2020)

IMPROVEMENTS:

* A new HTTP client based on `clj-http` has now been introduced, the default
  HTTP client based on `http-kit` has been cloned to `HttpKitHttpClient` and
  the default HTTP client has now been deprecated pending a move to the 
  `clj-http` client as the default in version 6.0.0.
* If a navigation response indicates a redirect but there is no URL in a
  Location header, a more descriptive error is now thrown.  

## 5.1.1 (June 15th, 2020)

BUGFIXES:

* `halbot.resource/get-href` now correctly handles vectors of hrefs.

## 5.1.0 (April 29th, 2019)

IMPROVEMENTS:

* If a non-JSON response body is received after a navigation, a more descriptive
  error is now thrown.

## 5.0.0 (February 21st, 2019)

BACKWARDS INCOMPATIBILITIES / NOTES:

* Resources no longer add links, embedded resources or properties when their
  value is `nil`.  

## 4.0.0 (August 12th, 2018)

BACKWARDS INCOMPATIBILITIES / NOTES:

* Prior to this version, the query parameter map passed to the underlying HTTP
client received a map with `String` keys and values of the type passed to the
`navigator` function. In this release, all values are converted to `String`s or
lists of `String`s to better support URI templating. If tests of code that use
halboy make use of `http-kit.fake` they may need to be updated to match the 
new query parameter format.

IMPROVEMENTS:

* `navigator` now fully supports URI templates as defined in 
[RFC6570](https://tools.ietf.org/html/rfc6570) (level 4).

## 3.1.2 (August 9th, 2018)

IMPROVEMENTS:

* `navigator` now allows multiple templated query parameters in templated hrefs.

## 3.1.1 (August 2nd, 2018)

IMPROVEMENTS:

* `navigator` now supports HTTP HEAD requests.

## 3.1.0 (June 11th, 2018)

IMPROVEMENTS:

* You can now pass a `String` when creating a resource and the full self link 
will be created for you.
* You can now create resources with multiple hrefs for the same link name. 

## 3.0.0 (February 14th, 2018)

IMPROVEMENTS:

* You can now specify your own JSON HTTP client.
* You can now pass global request options through to the embedded HTTP client.
* `resource/get-links` has been renamed to `resource/links`.
* `navigator/options` has been renamed to `navigator/settings`.

