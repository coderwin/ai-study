# Cursor에서 MCP 사용하기

## 설치

### 원클릭 (Plugin / Add to Cursor)

1. **Customize → Plugins** 또는 마켓에서 **Add to Cursor**
2. Slack: Agent 채팅에서 `/add-plugin slack` 도 가능

Gmail·Slack 등에서 **Failed to install …** 가 나오면 Cursor 쪽 원클릭 설치가 끊긴 경우가 많다. **수동 `mcp.json`** 으로 우회한다.

### 수동 (`mcp.json`)

| 위치 | 범위 |
|------|------|
| `.cursor/mcp.json` | 이 프로젝트만 |
| `~/.cursor/mcp.json` | Cursor 전역 |

저장 후 Cursor를 **완전히 종료**했다가 다시 연다.

### Plugin 설치 시: Add for Myself vs Add to Project

| 옵션 | 의미 |
|------|------|
| **Add for Myself** | 내 Cursor 계정 전역에서 MCP 사용 |
| **Add to Project** | 현재 프로젝트(예: `ai 사용하기`)에서만 사용 |

기능은 같고 **설정이 저장되는 범위**만 다르다. Add 실패는 선택과 무관하게 발생할 수 있다.

## 연결 확인

1. **Customize → MCPs** — 서버 Enable, Connect(OAuth)
2. 채팅 상단 **Available Tools**
3. `Ctrl+Shift+U` → **MCP Logs**

## 채팅에서 쓰기

**Agent** 모드에서 자연어로 요청한다. 도구 실행 전 승인 창이 뜨면 허용한다.

예: `#alarmtest 채널에 "나는 lhj" 보내줘`

## Gmail (참고)

공식 Gmail MCP는 Google Cloud OAuth·Developer Preview 등 **Add 한 번으로 끝나지 않는** 경우가 많다. Plugin Add 실패 시 `url` + `auth`(CLIENT_ID, CLIENT_SECRET, scopes) 수동 설정. **CLIENT_SECRET은 public repo에 커밋하지 않는다.**
