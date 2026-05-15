# Changelog

All notable changes to the Solenopsis Session library will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.12] - 2024-12-03

### Changed
- Replaced all wildcard imports with explicit imports for better code clarity
- Updated Solenopsis SOAP dependency to 1.8
- All test files now use explicit static imports from JUnit assertions

### Improved
- Better IDE support and code readability
- Clearer dependencies in test files

## [1.11] - 2024-12-03

### Added
- Comprehensive test suite with 14 unit tests
  - `CredentialsTest` - 7 tests for credentials management
  - `SessionContextTest` - 5 tests for session context
  - Existing `PortProxyTest` and `SoapExceptionUtilTest`
- Comprehensive README with architecture and usage examples
- CHANGELOG.md for version tracking
- Security best practices documentation
- Error handling examples

### Changed
- Updated Solenopsis SOAP dependency to 1.8
- Updated Apache Commons Lang3 to 3.18.0
- Updated Mockito to 5.15.2
- Enhanced JavaDoc throughout

### Improved
- Better documentation for credential management
- Security guidelines for production deployments
- Examples for all major use cases

## [1.10] - Earlier

### Changed
- Dependency updates
- Bug fixes

## Earlier Versions

Previous versions (1.0 - 1.9) included:
- Initial session management implementation
- Login/logout services for Enterprise, Partner, and Tooling APIs
- Session context record with Java 17 records
- Port proxy for authenticated SOAP calls
- Credentials management

---

## Upgrade Guide

### Upgrading to 1.12

#### Improvements You Get

- ✅ Explicit imports (no wildcards)
- ✅ Updated SOAP dependency to 1.8
- ✅ Transitive update to FlossWare Commons 1.10

#### No Breaking Changes

Version 1.12 is fully backward compatible with 1.11. No code changes required.

### Upgrading to 1.11

#### Improvements You Get

- ✅ Comprehensive test coverage (14 tests)
- ✅ Detailed README with examples
- ✅ Security best practices guide
- ✅ Latest dependencies (SOAP 1.7, Commons Lang3 3.18)
- ✅ Better error handling documentation

#### No Breaking Changes

Version 1.11 is fully backward compatible with 1.10. No code changes required.

#### Dependency Updates

If you're upgrading from earlier versions, ensure you have compatible dependencies:

```xml
<dependency>
    <groupId>org.solenopsis</groupId>
    <artifactId>soap</artifactId>
    <version>1.8</version>
</dependency>
```

The SOAP dependency was updated, which transitively updates FlossWare Commons to 1.10.

### Security Recommendations

When upgrading, review the new security best practices in the README:

1. **Never commit credentials** - Use environment variables
2. **Rotate tokens regularly** - Update security tokens periodically
3. **Separate sandbox/prod** - Different credentials for each environment
4. **Handle session expiration** - Implement retry logic for expired sessions

### New Examples Available

The README now includes complete examples for:
- Creating credentials for production and sandbox
- Logging in and out
- Creating authenticated SOAP ports
- Error handling
- Session refresh

See the README for full details.
