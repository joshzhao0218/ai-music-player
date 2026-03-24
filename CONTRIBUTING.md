# Contributing to AI Music Player

Thank you for your interest in contributing! This document provides guidelines for contributing to the project.

## 🎯 How to Contribute

### Reporting Bugs

Before creating bug reports, please check existing issues. When creating a bug report, include:

- **Clear title and description**
- **Steps to reproduce** the behavior
- **Expected vs actual behavior**
- **Screenshots** if applicable
- **Environment details** (OS, Java version, etc.)

**Example:**
```markdown
**Bug**: Music doesn't pause when clicking pause button

**Steps to Reproduce:**
1. Start playing music
2. Click pause button
3. Music continues playing

**Expected:** Music should pause
**Actual:** Music continues

**Environment:** macOS 14.0, Java 17, Chrome 120
```

### Suggesting Features

Feature suggestions are welcome! Please provide:

- **Use case**: Why is this feature needed?
- **Proposed solution**: How should it work?
- **Alternatives considered**: Other approaches
- **Additional context**: Screenshots, mockups, etc.

### Pull Requests

1. **Fork** the repository
2. **Create a branch** from `main`:
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes**
4. **Write/update tests**
5. **Ensure all tests pass**:
   ```bash
   mvn test
   ```
6. **Follow code style** (see below)
7. **Update documentation** if needed
8. **Submit PR** with clear description

## 📝 Code Style

### Java Conventions

- Use **camelCase** for variables and methods
- Use **PascalCase** for classes
- Use **UPPER_CASE** for constants
- Keep methods **small and focused** (max 30 lines)
- Add **JavaDoc** for public methods

**Example:**
```java
/**
 * Play music based on user command.
 * @param command User's natural language command
 */
public void playMusic(String command) {
    // Implementation
}
```

### Commit Messages

Follow [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `style:` - Code style changes (formatting)
- `refactor:` - Code refactoring
- `test:` - Adding tests
- `chore:` - Maintenance tasks

**Examples:**
```bash
feat: add sleep timer functionality
fix: resolve volume control bug
docs: update API documentation
test: add unit tests for MusicService
```

### Testing Requirements

- Write tests for **new features**
- Maintain **>80% code coverage**
- Use **descriptive test names**:
  ```java
  @Test
  void testPlayMusic_ShouldSetPlayingStateToTrue() {
      // Test implementation
  }
  ```

## 🏗️ Architecture Guidelines

### Layer Separation

- **Controller**: Handle HTTP requests/responses only
- **Service**: Business logic
- **Model**: Data structures
- **Repository**: Data access (when implemented)

**Don't** mix layers:
```java
// ❌ Bad: Controller accessing database directly
@PostMapping("/music")
public void playMusic() {
    database.executeQuery(...);
}

// ✅ Good: Controller delegating to service
@PostMapping("/music")
public ApiResponse playMusic() {
    musicService.play();
    return ApiResponse.ok(...);
}
```

### Error Handling

- Use **GlobalExceptionHandler** for consistent errors
- Return **meaningful error messages**
- Log errors with **appropriate level**

```java
// ✅ Good
throw new IllegalArgumentException("Volume must be between 0 and 100");

// ❌ Bad
throw new Exception("Error");
```

## 🧪 Testing

### Running Tests

```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=MusicServiceTest

# With coverage
mvn clean test jacoco:report
```

### Writing Tests

- Use **descriptive test names**
- Follow **AAA pattern** (Arrange, Act, Assert)
- Test **edge cases**
- Mock **external dependencies**

```java
@Test
void testSetVolume_ShouldCapAtMaximum() {
    // Arrange
    int invalidVolume = 150;
    
    // Act
    musicService.setVolume(invalidVolume);
    
    // Assert
    assertEquals(100, musicService.getVolume());
}
```

## 📚 Documentation

### When to Update Docs

- Adding new **API endpoints**
- Changing **configuration options**
- Adding **features**
- Fixing **bugs** that change behavior

### Documentation Standards

- Use **clear, concise language**
- Include **code examples**
- Add **screenshots** for UI changes
- Keep **API docs** in sync with code

## 🚀 Development Workflow

### Branch Strategy

- `main` - Production-ready code
- `develop` - Integration branch (optional)
- `feature/*` - New features
- `fix/*` - Bug fixes
- `hotfix/*` - Urgent production fixes

### Review Process

1. **Create PR** with clear description
2. **Wait for review** (usually within 48 hours)
3. **Address feedback**
4. **Squash commits** if requested
5. **Merge** after approval

### PR Template

```markdown
## Description
Brief description of changes

## Type of Change
- [ ] Bug fix
- [ ] New feature
- [ ] Breaking change
- [ ] Documentation update

## Testing
- [ ] Tests added/updated
- [ ] All tests pass
- [ ] Manual testing completed

## Checklist
- [ ] Code follows style guidelines
- [ ] Self-review completed
- [ ] Documentation updated
- [ ] No new warnings
```

## 💡 Tips for Contributors

### Good First Issues

Look for issues labeled:
- `good first issue` - Beginner-friendly
- `help wanted` - Need assistance
- `documentation` - Doc improvements

### Getting Help

- Check **existing issues** and **discussions**
- Read **DEVELOPMENT.md** for setup guide
- Join **Discussions** tab on GitHub
- Ask in **PR comments**

### Common Mistakes to Avoid

- ❌ Large PRs (>500 lines)
- ❌ Missing tests
- ❌ Incomplete documentation
- ❌ Breaking changes without notice
- ❌ Ignoring code style

## 📜 License

By contributing, you agree that your contributions will be licensed under the MIT License.

## 🙏 Thank You!

Every contribution makes this project better. Thank you for your time and effort!

---

**Questions?** Open an issue or discussion on GitHub.

**Last Updated**: 2026-03-24
