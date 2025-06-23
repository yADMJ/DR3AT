package Ex.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Cliente {
    private static final String BASE_URL = "http://localhost:7000";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        // Chama métodos em gerais
        criarUsuario();
        getUsuarios();
        getIdUsuario("algum-id");
        getStatus();
        postEcho("Teste de mensagem");
    }

    private static void criarUsuario() throws Exception {
        URL url = new URL(BASE_URL + "/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Corpo JSON da requisição com dados do usuário
        String jsonInput = "{\"nome\":\"Bagri\",\"email\":\"Bagribs@example.com\",\"idade\":2}";
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Imprime código de resposta HTTP do POST
        System.out.println("POST /tarefas Response Code: " + conn.getResponseCode());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Response: " + br.readLine());
        }
        conn.disconnect();
    }

    private static void getUsuarios() throws Exception {
        URL url = new URL(BASE_URL + "/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        System.out.println("GET /tarefas Response Code: " + conn.getResponseCode());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Response: " + br.readLine());
        }
        conn.disconnect();
    }

    private static void getIdUsuario(String id) throws Exception {
        URL url = new URL(BASE_URL + "/tarefas/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");


        System.out.println("GET /tarefas/" + id + " Response Code: " + conn.getResponseCode());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() >= 400 ? conn.getErrorStream() : conn.getInputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Response: " + br.readLine());
        }
        conn.disconnect();
    }

    private static void getStatus() throws Exception {
        URL url = new URL(BASE_URL + "/status");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");


        System.out.println("GET /status Response Code: " + conn.getResponseCode());
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Response: " + br.readLine());
        }
        conn.disconnect();
    }

    private static void postEcho(String mensagem) throws Exception {
        URL url = new URL(BASE_URL + "/echo"); // Define a URL do endpoint /echo
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST"); // Método POST
        conn.setRequestProperty("Content-Type", "application/json"); // Define o tipo do conteúdo como JSON
        conn.setDoOutput(true); // Indica que vai enviar corpo na requisição

        // Cria o JSON com a mensagem para enviar
        String jsonInput = "{\"mensagem\":\"" + mensagem + "\"}";

        // Envia o JSON no corpo da requisição
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Imprime o código de resposta da requisição
        System.out.println("POST /echo Response Code: " + conn.getResponseCode());

        // Lê e imprime a resposta da requisição
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                conn.getResponseCode() >= 400 ? conn.getErrorStream() : conn.getInputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Response: " + br.readLine());
        }

        conn.disconnect(); // Fecha a conexão
    }

}
