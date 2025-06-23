import Ex.Controle; // Importa a classe de controle que implementa os endpoints
import io.javalin.Javalin;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given; // Método estático para montar requisições REST
import static org.hamcrest.Matchers.equalTo; // Matcher para comparação exata em asserts

public class AppTest {
    private static Javalin app;

    @BeforeAll
    public static void setup() {
        // Inicializa e inicia o servidor na porta 7000
        app = Javalin.create().start(7000);
        // Define rotas e os métodos correspondentes da classe Controle
        app.get("/hello", Controle::getHello);
        app.get("/status", Controle::getStatus);
        app.post("/echo", Controle::postEcho);
        app.get("/saudacao/{nome}", Controle::getSaudacao);
        app.post("/tarefas", Controle::criarUsuario);
        app.get("/tarefas", Controle::getUsuarios);
        app.get("/tarefas/{id}", Controle::getIdUsuario);
        RestAssured.baseURI = "http://localhost:7000";
    }

    @AfterAll
    public static void tearDown() {
        // Para o servidor após todos os testes
        app.stop();
    }

    @Test
    public void testHelloEndpoint() {
        // Testa o endpoint GET /hello
        given()
                .when()
                .get("/hello")
                .then()
                .statusCode(200) // Espera status HTTP 200 OK
                .body(equalTo("Hello, Javalin!")); // Espera corpo exato "Hello, Javalin!"
    }

    @Test
    public void testCreateUser() {
        // JSON para criar um usuário novo
        String userJson = "{\"nome\":\"AdemirJr\",\"email\":\"Adm2Bala@example.com\",\"idade\":21}";
        // Testa endpoint POST /tarefas para criação do usuário
        given()
                .contentType(ContentType.JSON) // Define conteúdo JSON no request
                .body(userJson) // Corpo da requisição
                .when()
                .post("/tarefas")
                .then()
                .statusCode(201) // Espera status HTTP 201 Created
                .body("nome", equalTo("AdemirJr")) // Verifica valor do campo nome
                .body("email", equalTo("Adm2Bala@example.com")) // Verifica email
                .body("idade", equalTo(21)); // Verifica idade
    }

    @Test
    public void testGetUserById() {
        // Cria usuário para pegar o ID gerado
        String userJson = "{\"nome\":\"Bagri\",\"email\":\"BAgriPeixe@example.com\",\"idade\":2}";
        String id = given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/tarefas")
                .then()
                .extract()
                .path("id"); // Extrai o campo "id" da resposta JSON

        // Testa endpoint GET /tarefas/{id} para buscar usuário pelo ID
        given()
                .when()
                .get("/tarefas/" + id)
                .then()
                .statusCode(200) // Espera status HTTP 200 OK
                .body("nome", equalTo("Bagri"))
                .body("email", equalTo("BAgriPeixe@example.com"))
                .body("idade", equalTo(2));
    }

    @Test
    public void testGetAllUsers() {
        // Cria usuário para garantir que há pelo menos um na lista
        String userJson = "{\"nome\":\"Ozzy\",\"email\":\"OzzyDremes@example.com\",\"idade\":22}";
        given()
                .contentType(ContentType.JSON)
                .body(userJson)
                .when()
                .post("/tarefas");

        // Testa endpoint GET /tarefas para listar todos usuários
        given()
                .when()
                .get("/tarefas")
                .then()
                .statusCode(200) // Espera status HTTP 200 OK
                .body("size()", equalTo(1)) // Espera que haja exatamente 1 usuário na lista
                .body("[0].nome", equalTo("Ozzy")); // Verifica nome do primeiro usuário da lista
    }
}
