# Solenopsis Session

Salesforce session management and authentication library for Java.

[![Build Status](https://github.com/solenopsis/session/workflows/CD-CI/badge.svg)](https://github.com/solenopsis/session/actions)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

## Overview

This library provides high-level session management and authentication for Salesforce SOAP APIs. It handles:

- **Login/Logout** - Authenticate with Salesforce and manage session lifecycle
- **Session Context** - Maintain session state (session ID, server URL, user info)
- **Multiple API Support** - Enterprise, Partner, and Tooling API authentication
- **Port Management** - Automatic SOAP port configuration with session headers

## Features

- Type-safe authentication with Java records
- Automatic session header management for SOAP calls
- Support for production and sandbox environments
- Session context with all necessary Salesforce connection details
- Built on [Solenopsis SOAP](https://github.com/solenopsis/soap) for SOAP clients

## Installation

### Maven

```xml
<dependency>
    <groupId>org.solenopsis</groupId>
    <artifactId>session</artifactId>
    <version>1.14</version>
</dependency>

<repositories>
    <repository>
        <id>flossware-packagecloud</id>
        <url>https://packagecloud.io/flossware/java/maven2</url>
    </repository>
</repositories>
```

## Usage Examples

### Create Credentials

```java
import org.solenopsis.session.credentials.CredentialsRecord;
import org.solenopsis.session.Credentials;

// For production
Credentials prodCreds = new CredentialsRecord(
    "https://login.salesforce.com",     // URL
    "user@company.com",                  // Username
    "myPassword123",                     // Password
    "mySecurityToken456",                // Security token
    "58.0"                               // API version
);

// For sandbox
Credentials sandboxCreds = new CredentialsRecord(
    "https://test.salesforce.com",       // Sandbox URL
    "user@company.com.sandbox",          // Sandbox username
    "myPassword123",                     // Password
    "sandboxToken789",                   // Security token
    "58.0"                               // API version
);
```

### Login to Salesforce

```java
import org.solenopsis.session.soap.login.LoginServiceEnum;
import org.solenopsis.session.SessionContext;

// Login using Enterprise API
SessionContext session = LoginServiceEnum.ENTERPRISE.login(prodCreds);

// Session context contains:
System.out.println("Session ID: " + session.sessionId());
System.out.println("Server URL: " + session.serverUrl());
System.out.println("User ID: " + session.userId());
System.out.println("Is Sandbox: " + session.isSandbox());
```

### Use Session with SOAP Port

```java
import org.solenopsis.session.soap.ProxyPortEnum;
import org.solenopsis.soap.metadata.MetadataPortType;

// Create authenticated port
MetadataPortType metadataPort = ProxyPortEnum.METADATA.createPort(session);

// Now use the port for API calls - session header is automatically set
// metadataPort.deploy(...);
// metadataPort.retrieve(...);
```

### Logout

```java
// Logout from Salesforce
LoginServiceEnum.ENTERPRISE.logout(session);
```

### Refresh Session

```java
// Create new session context with updated session ID
SessionContext newSession = session.createNewSessionContext(newSessionId);
```

## Architecture

### Package Structure

```
org.solenopsis.session
├── credentials/          # Credentials implementations
│   ├── CredentialsRecord.java
│   ├── CredentialsUtil.java
│   └── PropertiesCredentialsEnum.java
├── login/                # Login/logout services
│   ├── LoginService.java (interface)
│   ├── LoginException.java
│   └── LogoutException.java
├── soap/
│   ├── login/            # SOAP login implementations
│   │   ├── EnterpriseLoginService.java
│   │   ├── PartnerLoginService.java
│   │   ├── ToolingLoginService.java
│   │   └── LoginServiceEnum.java
│   ├── util/             # SOAP utilities
│   ├── PortProxy.java    # Authenticated port wrapper
│   └── ProxyPortEnum.java # Port factory with session
├── Credentials.java      # Credentials interface
└── SessionContext.java   # Session state record
```

### Login Process Flow

```
1. Create Credentials
   ↓
2. Call LoginService.login(credentials)
   ↓
3. Receive SessionContext
   ↓
4. Use SessionContext to create authenticated ports
   ↓
5. Make SOAP API calls
   ↓
6. Call LoginService.logout(sessionContext) when done
```

## API Support

| API | Login Service | Use Case |
|-----|---------------|----------|
| **Enterprise** | `LoginServiceEnum.ENTERPRISE` | Strongly-typed data API |
| **Partner** | `LoginServiceEnum.PARTNER` | Dynamically-typed data API |
| **Tooling** | `LoginServiceEnum.TOOLING` | Developer tools, metadata |

## Session Context

The `SessionContext` record contains all information about an authenticated session:

```java
public record SessionContext(
    String medataServerUrl,      // Metadata API endpoint
    boolean isPasswordExpired,   // Password expiration flag
    boolean isSandbox,           // Production vs. sandbox
    String serverUrl,            // Server base URL (normalized)
    String sessionId,            // Authentication token
    String userId,               // Salesforce user ID
    ServiceEnum service,         // API service used
    Credentials credentials      // Original credentials
)
```

## Requirements

- **Java 17+**
- **Solenopsis SOAP 1.9+** - Salesforce SOAP clients
- **FlossWare Commons 1.10+** - Foundation utilities (transitive)
- **Apache CXF 4.0+** - SOAP framework (transitive)

## Testing

The library includes comprehensive unit tests:

```bash
# Run all tests
mvn test

# Build without tests
mvn clean install -DskipTests
```

**Test Coverage:**
- Credentials tests - 7 tests
- SessionContext tests - 5 tests
- Port proxy tests - 1 test
- SOAP utility tests - 1 test
- **Total: 14 tests** with 0 failures

## Security Considerations

### Credential Storage

- **Never commit credentials to version control**
- Use environment variables or secure configuration management
- Rotate security tokens regularly
- Use separate credentials for sandbox and production

### Session Management

- Session IDs are sensitive - treat like passwords
- Sessions expire after inactivity (Salesforce default: 2 hours)
- Always logout when done to free server resources
- Handle `LoginException` and refresh sessions as needed

### Best Practices

```java
// ✅ Good - credentials from environment
Credentials creds = new CredentialsRecord(
    System.getenv("SFDC_URL"),
    System.getenv("SFDC_USERNAME"),
    System.getenv("SFDC_PASSWORD"),
    System.getenv("SFDC_TOKEN"),
    "58.0"
);

// ❌ Bad - hardcoded credentials
Credentials badCreds = new CredentialsRecord(
    "https://login.salesforce.com",
    "admin@company.com",
    "hardcodedPassword123",  // DON'T DO THIS!
    "hardcodedToken456",     // DON'T DO THIS!
    "58.0"
);
```

## Error Handling

```java
import org.solenopsis.session.login.LoginException;
import org.solenopsis.session.login.LogoutException;

try {
    SessionContext session = LoginServiceEnum.ENTERPRISE.login(credentials);
    
    // ... use session ...
    
    LoginServiceEnum.ENTERPRISE.logout(session);
} catch (LoginException e) {
    // Handle authentication failure
    System.err.println("Login failed: " + e.getMessage());
} catch (LogoutException e) {
    // Handle logout issues (less critical)
    System.err.println("Logout failed: " + e.getMessage());
}
```

## Building from Source

```bash
git clone https://github.com/solenopsis/session.git
cd session
mvn clean install
```

## Dependencies

This library depends on:
- **[solenopsis/soap](https://github.com/solenopsis/soap)** - Salesforce SOAP clients
- **[FlossWare/commons](https://github.com/FlossWare/commons)** - Utility functions (transitive)

## Contributing

1. Ensure all tests pass: `mvn test`
2. Follow existing code patterns
3. Add tests for new functionality
4. Update documentation

## License

GNU General Public License, Version 3 - See [LICENSE](LICENSE) file

## Links

- **Source**: https://github.com/solenopsis/session
- **Issues**: https://github.com/solenopsis/session/issues
- **SOAP Library**: https://github.com/solenopsis/soap
- **Commons Library**: https://github.com/FlossWare/commons
- **Package Repository**: https://packagecloud.io/flossware/java

## Salesforce Documentation

- [Authentication](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_calls_login.htm)
- [Session Management](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/sforce_api_concepts_security.htm)
- [API Versions](https://developer.salesforce.com/docs/atlas.en-us.api.meta/api/api_versions.htm)
- [Security Tokens](https://help.salesforce.com/s/articleView?id=sf.user_security_token.htm)
