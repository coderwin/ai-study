# local-java MCP (stdio)

Spring Boot + MCP Java SDK로 만든 **로컬 stdio MCP 서버** 예제다.

## 빌드

```bash
cd local-mcp-server
./mvnw.cmd package -DskipTests
```

JAR: `target/my-mcp-1.0.1.jar`

## Cursor

`.cursor/mcp.json`의 `local-java` 항목이 위 JAR을 `java -jar`로 실행한다.  
Cursor 재시작 후 **Customize → MCPs**에서 `local-java`를 켠다.

## 도구

- `hello` — 인자 `name`을 받아 인사 문자열 반환
