//1 - pacote
package petstore;
//2- bibliotecas
import org.testng.annotations.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

//3- classe
public class Pet {
//3.1 atributos - são as características de um objeto (altura,rg,peso, nome)
    String uri = "https://petstore.swagger.io/v2/pet"; //endereço da entidade pet

//3.2 metodos - são ações que não retorna nenhum valor (que faz e acabou)
//    funções - tem uma ação e retorna um valor/resultado
// realiza a leitura do arquivo json e devolve conteudo
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }
    // incluir - create - post
    @Test (priority = 1)// identifica o metodo ou função com teste para o testng
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("dados/pet1.json");

        //sintaxe Gherkin
        //dado - quando - então
        //Given - When - Then

    given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
    .when()
                .post(uri)
    .then()
                .log().all()
                .statusCode(200)
                .body("name",is("Bili"))
                .body("status",is("available"))
                .body("category.name",is("cat"))
        ;
    }
@Test (priority = 2)
    public void consultarPet(){
        String petId = "2407";
    given()
             .contentType("application/json")
             .log().all()
    .when()
            .get(uri + "/" + petId)
    .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Bili"))
            .body("category.name", is("cat"))
            .body("status", is("available"))
    ;
    }
 @Test (priority = 3)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("dados/pet2.json");
    given()
             .contentType("application/json")
             .log().all()
             .body(jsonBody)
    .when()
            .put(uri)
    .then()
            .log().all()
            .statusCode(200)
            .body("name", is("Bili"))
            .body("status", is("adotada"))
    ;
 }
}

