package com.example.local_mcp_server;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.modelcontextprotocol.json.McpJsonDefaults;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpServerFeatures.SyncToolSpecification;
import io.modelcontextprotocol.server.transport.StdioServerTransportProvider;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;
import io.modelcontextprotocol.spec.McpSchema.ServerCapabilities;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import io.modelcontextprotocol.spec.McpSchema.Tool;

/**
 * 내 PC에서 돌아가는 MCP 서버.
 * Cursor가 이 프로그램을 실행한 뒤, 채팅 요청에 맞춰 hello 도구를 호출한다.
 */
@SpringBootApplication
public class LocalMcpServerApplication {

	public static void main(String[] args) {
		// Cursor와 이 프로그램은 표준 입출력(stdin/stdout)으로 JSON 메시지를 주고받는다.
		// 로그는 표준 출력이 아니라 표준 에러로 보내야 이 통신이 깨지지 않는다.
		var transportProvider = new StdioServerTransportProvider(McpJsonDefaults.getMapper());

		// hello 도구가 받는 입력 형식. name이라는 문자열 하나가 반드시 있어야 한다.
		String schema = """
				{
					"type": "object",
					"properties": {
						"name": {"type": "string", "description": "인사할 이름"}
					},
					"required": ["name"]
				}
				""";

		// Cursor에게 보여줄 도구 정의.
		// 이름(hello)과 설명은 AI가 이 도구를 쓸지 판단할 때 사용한다.
		var hello = SyncToolSpecification.builder()
				.tool(Tool.builder("hello", McpJsonDefaults.getMapper(), schema)
						.description("이름을 받아 인사 문자열을 반환한다")
						.build())
				// Cursor가 hello를 호출하면 이 코드가 실행된다.
				.callHandler((exchange, request) -> {
					Object raw = request.arguments().get("name");
					String name = raw == null ? "world" : raw.toString();
					// 예: name이 "테스트 v5"이면 "안녕, [테스트 v5]"를 돌려준다.
					return CallToolResult.builder()
							.content(List.of(new TextContent("안녕, [" + name + "]")))
							.build();
				})
				.build();

		// 서버 이름, 버전, 제공 기능(도구 사용 가능)을 알리고 hello 도구를 등록한다.
		McpServer.sync(transportProvider)
				.serverInfo("local-java-mcp", "1.0.0")
				.capabilities(ServerCapabilities.builder().tools(true).build())
				.tools(hello)
				.build();

		// main이 바로 끝나면 프로세스가 꺼져서 Cursor가 도구를 호출할 수 없다.
		// 현재 스레드를 멈춰 서버를 계속 켜 둔다.
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

	}

}
