# AlkemyDisney
Repositorio para Challenge de Alkemy - Final

:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

Descargar este repositorio de forma local y abrirlo con intelliJ Idea
Crear una Base de datos MySql con el nombre "disney"
importar con Postman el archivo Disney.postman_collection.json 
Dentro de Postman realizar lo siguiente:

  Dentro de la folder auth: 
      1- Ir a singup y enviar un request, este request le enviara un mail a la dirección de correo que usted especifique en el Json.
         Tener en cuenta que el campo password debe tener al menos 8 caracteres de longitus.
         
      2- Ir a singin y enviar el request, este devolvera un token jwt, copiar ese token.
     
  Prueba de Endpoints:
      1 - Para acceder a los endpoints hay que agregar el siguiente header
            Key: "Authorization"
            Value: Bearer valor del token jwt
            
            Se dejo en el endpoint llamado personaje-todos esto realizado con un token jwt que debe ser reemplazado.
            
  Aclaración envio de mail con SendGrid:
  
        El mail puede llegar a la bandeja de Spam revisar.

