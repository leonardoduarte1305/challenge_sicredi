# challenge_sicredi

  Para conferir todas as funcionalidades online acesse: https://devback-sicredi.herokuapp.com/swagger-ui/index.html
  
  Para conferir as funcionalidades localmente:
  <ul>
    <li>Clone o projeto</li>
    <li>Rode no Docker um container de MySQL: </li>
    <li>docker run -d --name db_leo_sicredi_mysql_dev -e MYSQL_ROOT_PASSWORD=senhaSegura -e MYSQL_DATABASE=sicredi -p 3306:3306 mysql:latest</li>
    <li>O projeto está configurado para subir em modo de desenvolvimento.</li>
    <li>em http://localhost:8080/swagger-ui/index.html você pode conferir todas as funcionalidades.</li>
  </ul>

  Para conferir as funcionalidades localmente em modo de producao:
  <ul>
   <li>Clone o projeto</li>
   <li>Rode no Docker um container de MySQL: </li>
   <li>docker run -d --name db_leo_sicredi_mysql_prod -e MYSQL_ROOT_PASSWORD=senhaSegura -e MYSQL_DATABASE=dbProd -p 3306:3306 mysql:latest</li>
   <li>Para habilitar o modo de produção algumas variáveis de ambiente são necessárias:</li>
   <li>{PROFILE_ACTIVE} = prod</li>
   <li>{DATABASE_USERNAME} = root</li>
   <li>{DATABASE_PASSWORD} = senhaSegura</li>
   <li>{DATABASE_URL} = jdbc:mysql://localhost:3306/dbProd</li>
   <li{PORT} = 8080 (opcional, padrão 8080)></li>
   <li>em http://localhost:8080/swagger-ui/index.html você pode conferir todas as funcionalidades.</li>
  </ul>

    Principais Tecnologias Utilizadas:
      - Spring Boot
      - Spring DataJPA
      - Mysql
      - SpringFox
      - JUnit Jupiter
      - Mockito
      - Docker
      
  Futuras adições:
    - Testes de integração para os controllers
    - Integração com a api de consulta por CPF
    - Spring Security
    - Criação de endpoints para Alteração e Exclusão de entidades
    - Criação da imagem Docker
