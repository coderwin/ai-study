# Slack MCP (Cursor)

## 공식 설정 (이 프로젝트)

`.cursor/mcp.json`:

```json
{
  "mcpServers": {
    "slack": {
      "url": "https://mcp.slack.com/mcp",
      "auth": {
        "CLIENT_ID": "3660753192626.8903469228982"
      }
    }
  }
}
```

- 문서: [Connect Slack MCP to Cursor](https://docs.slack.dev/ai/slack-mcp-server/connect-to-cursor/)
- **원격 MCP만** 지원. 로컬 `npx` Slack MCP 서버와는 다르다.
- 워크스pace **관리자 MCP 승인**이 필요할 수 있다.

## CLIENT_ID

`3660753192626.8903469228982` 는 Slack 문서에 공개된 **공용 OAuth 클라이언트 ID**다.  
Cursor ↔ Slack 호스팅 MCP 연동용이며, 개인 비밀키가 아니다.  
각 사용자는 **Connect(OAuth)** 로 자기 워크스pace 권한을 붙인다.

## 로컬 vs 원격

| | 로컬 MCP (`command` / npx) | 지금 Slack 설정 (`url`) |
|--|---------------------------|-------------------------|
| 서버 위치 | 내 PC 프로세스 | `mcp.slack.com` (Slack 클라우드) |
| API 호출 | 로컬 서버가 Slack API 호출 | **Slack MCP 서버**가 Slack API 호출 |

내 PC에서는 **Cursor(MCP 클라이언트)** 만 동작하고, Slack API를 직접 치는 MCP 서버 프로세스는 돌지 않는다.

## MCP vs 코드로 Slack 보내기 (차이 3가지)

1. **누가 API를 치는가** — MCP는 Agent가 등록된 도구만 호출하고, HTTP·토큰은 MCP 서버(Slack 호스팅)가 처리한다. 코드 방식은 Agent가 매번 스크립트를 만들고 실행한다.
2. **설정 vs 즉흥** — MCP는 `mcp.json` + OAuth 한 번. 코드는 대화마다 구현·명령이 달라질 수 있다.
3. **안전·일관성** — MCP는 도구 범위가 좁고 승인 가능. 코드는 터미널 권한이 넓고 실수·키 노출 위험이 크다.

## 테스트 예시

Agent: `Slack #alarmtest 채널에 "나는 lhj" 라고 메시지 보내줘`

흐름: `slack_search_channels` → 채널 ID → `slack_send_message`(channel_id, message)
