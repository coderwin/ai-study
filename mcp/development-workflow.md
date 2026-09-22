# 개발 과정에서 MCP 사용하기

MCP는 개발 중 **Cursor Agent가 쓰는 표준 도구(플러그)** 로 많이 쓴다.  
코드는 repo에서 수정하고, **이슈·Slack·DB·브라우저·배포 정보**는 MCP로 연결하는 식이다.

## 1. IDE에 도구를 붙이고 Agent가 호출

개발자가 매번 API 스크립트를 만들지 않고, **연결해 둔 MCP 도구**를 Agent가 고른다.

| MCP 예 | 개발 중 하는 일 |
|--------|------------------|
| GitHub | 이슈/PR 조회, 코멘트, CI 상태 |
| Slack | 배포 알림, 채널 검색, 메시지 |
| Linear / Jira | 티켓 읽기·생성 |
| 브라우저 MCP | 로컬 웹앱 열고 클릭·스크린샷 |
| DB MCP | 스키마·쿼리 (읽기 위주) |
| Notion / Docs | 스펙·회의록 참조 |

흐름: **채팅 → Agent → MCP 도구 → 외부 시스템** (Slack 메시지 보내기와 동일).

## 2. 프로젝트 vs 개인 설정

| 위치 | 범위 |
|------|------|
| `.cursor/mcp.json` | 이 repo / 팀 공통 |
| `~/.cursor/mcp.json` | Cursor 전역 |

팀은 “이 서비스 repo에는 GitHub + Slack만”처럼 범위를 나누기도 한다.

## 3. 코드 작성과 MCP 역할

- **MCP**: 이미 있는 시스템에 읽기·쓰기·검색
- **일반 코딩**: repo 파일 수정, 테스트, 빌드

Agent는 둘 다 쓸 수 있지만, MCP는 **repo 밖·이미 떠 있는 서비스**와 붙을 때 유리하다.

## 4. 로컬 MCP vs 원격 MCP

| | 로컬 (`command` / npx) | 원격 (`url`, 예: Slack) |
|--|------------------------|-------------------------|
| 예 | 사내 API 래퍼, 로컬 DB | `https://mcp.slack.com/mcp` |
| 장점 | 사내·민감 데이터를 PC 경계 안에서 | 설치 없음, OAuth·API는 벤더 관리 |
| 팀 | **자체 MCP 서버 구현** | **공식/마켓 MCP** 연결 |

자체 MCP 서버를 만들면 “우리 배포 API / 사내 문서 / 테스트 환경”만 도구로 노출할 수 있다.

## 5. 실무 워크플로 예

```text
1. 티켓 MCP로 요구사항 확인
2. repo에서 코드 수정
3. 터미널/테스트로 검증
4. GitHub MCP로 PR 생성·설명
5. Slack MCP로 #dev에 "PR 올림" 알림
```

전부 MCP일 필요는 없고, **반복·외부 연동** 구간에 MCP를 끼워 넣는 경우가 많다.

## 6. 주의점

- **승인 / Allowlist**: 메시지·배포 등은 실행 전 확인
- **비밀키**: `CLIENT_SECRET`, API 키는 repo에 커밋하지 않음 (Slack 공개 CLIENT_ID는 예외에 가까움)
- **Plugin Add 실패** 시 `mcp.json` 수동 설정

## 이 repo (`ai-study`)에서

- 문서: `mcp/` 아래 개념·Slack·프로토콜 정리
- 설정: `.cursor/mcp.json` Slack 원격 MCP
- 사용: Agent로 채널 알림 등 **개발 알림·실험**

GitHub MCP 등을 추가하면 PR/이슈도 같은 패턴으로 붙일 수 있다.
