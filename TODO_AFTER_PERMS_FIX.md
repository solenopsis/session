# TODO: Create GitHub Issues After Permissions Fix

## Problem
Cannot create issues in `solenopsis/session` (organization repo) because GitHub personal access token lacks required OAuth scopes for organization repositories.

## Error
```
GraphQL: Resource not accessible by personal access token (createIssue)
HTTP 403
```

## Current Permissions
- User: sfloess (admin on solenopsis/session)
- Repo type: Organization repository
- Token has: basic auth
- Token needs: `repo` and/or `write:org` scopes

## What to Do After Fix

Once permissions are updated, run these commands to create the issues:

### Issue #1: Duplicate GPL copyright headers in 7 files

```bash
gh issue create --repo solenopsis/session \
  --title "Duplicate GPL copyright headers in 7 files" \
  --label "bug" \
  --body "$(cat <<'EOF'
## Problem

Seven Java files contain duplicate GPL v3 copyright headers, with both a 2023 header and an older header (2015 or 2017) in the same file. This creates legal confusion and unnecessary bloat.

## Affected Files

1. `src/main/java/org/solenopsis/session/login/LoginException.java` (2023 + 2015)
2. `src/main/java/org/solenopsis/session/login/LoginService.java` (2023 + 2015)
3. `src/main/java/org/solenopsis/session/login/LogoutException.java` (2023 + 2015)
4. `src/main/java/org/solenopsis/session/soap/SoapUrlEnum.java` (2023 + 2017)
5. `src/main/java/org/solenopsis/session/soap/login/EnterpriseLoginService.java` (2023 + 2015)
6. `src/main/java/org/solenopsis/session/soap/login/PartnerLoginService.java` (2023 + 2015)
7. `src/main/java/org/solenopsis/session/soap/login/ToolingLoginService.java` (2023 + 2015)

## Impact

- **Legal confusion**: Two identical GPL licenses with different years appear in the same file
- **Code bloat**: 30-line headers instead of 15 lines
- **Maintenance burden**: Future copyright updates may miss one of the duplicate blocks
- **Professional appearance**: Duplicate headers look like an error

## Example

\`\`\`java
/* Copyright (C) 2023 Scot P. Floess
 * ...full GPL text...
 */
/*
 * Copyright (C) 2015 Scot P. Floess
 * ...full GPL text...
 */
package org.solenopsis.session.login;
\`\`\`

## Recommended Fix

Consolidate to a single header with a year range:

\`\`\`java
/* Copyright (C) 2015-2023 Scot P. Floess
 * ...GPL text...
 */
\`\`\`

Or keep only the earliest year if that's the project's copyright policy.

## Related

Created from commit 7ac09fc which added GPL headers to all files but didn't remove existing headers.

---
*Identified by AI code review on 2026-06-05*
EOF
)"
```

### Issue #2: Add Maven license plugin for automated copyright header management

```bash
gh issue create --repo solenopsis/session \
  --title "Add Maven license plugin for automated copyright header management" \
  --label "enhancement" \
  --body "$(cat <<'EOF'
## Problem

The project manually maintains GPL copyright headers across 43 Java files (645 lines of duplicated text). Any update to copyright years, author information, or license text requires manually editing all 43 files, with high risk of inconsistency.

## Current State

- **No automation**: pom.xml has no license management plugins
- **Manual maintenance**: Headers were added via commit 7ac09fc by copy-pasting
- **No validation**: CI/CD pipeline doesn't verify header presence or format
- **No template**: No single source of truth for header content

## Impact

- Updates require editing 43+ files manually
- New files may be added without headers (no enforcement)
- Header format can diverge across files
- Time-consuming maintenance for yearly copyright updates

## Recommended Solution

Add \`license-maven-plugin\` (Mycila) to pom.xml:

\`\`\`xml
<plugin>
    <groupId>com.mycila</groupId>
    <artifactId>license-maven-plugin</artifactId>
    <version>4.3</version>
    <configuration>
        <header>LICENSE-HEADER.txt</header>
        <properties>
            <owner>Scot P. Floess</owner>
            <year>\${project.inceptionYear}</year>
        </properties>
        <includes>
            <include>src/**/*.java</include>
        </includes>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>check</goal>
            </goals>
        </execution>
    </executions>
</plugin>
\`\`\`

Create \`LICENSE-HEADER.txt\` template:

\`\`\`
Copyright (C) \${year} \${owner}

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
\`\`\`

Add CI validation to \`.github/workflows/main.yml\`:

\`\`\`yaml
- name: Check license headers
  run: mvn license:check
\`\`\`

## Benefits

- **Automated updates**: \`mvn license:format\` updates all headers at once
- **CI enforcement**: Build fails if headers are missing or incorrect
- **Consistency**: Single template ensures uniform formatting
- **Low maintenance**: Copyright year can use Maven properties

## Alternative

Consider using SPDX short-form identifiers (\`SPDX-License-Identifier: GPL-3.0-or-later\`) to reduce header size from 15 lines to 1 line.

---
*Identified by AI code review on 2026-06-05*
EOF
)"
```

## Alternative: Manual Creation

If the commands above don't work, the full issue content is available in:
- `GITHUB_ISSUES.md` - Copy/paste content for manual web UI creation

## Code Review Documentation

The complete AI transparency report and findings are in:
- `CODE_REVIEW_FINDINGS.md` - Full review report with methodology, findings, and recommendations

## Summary of Findings

From AI code review of commit 7ac09fc:
- **8 findings total**: 7 duplicate header bugs + 1 process/tooling issue
- **Severity**: Medium (legal confusion, maintenance burden)
- **Verification**: All findings independently verified by AI agents
- **Token usage**: ~187k tokens across 10 agents (7 finders + 3 verifiers)
- **Duration**: ~21 minutes

---

**Action needed**: Fix GitHub CLI permissions, then run the commands above or create issues manually.
