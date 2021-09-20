Árbol binario b+

Búsqueda de elementos con el algoritmo de árbol binario b+ y almacenando en archivo txt, desarrollado como EndPoint.
La inserción y búsqueda se realiza desde memoria, el archivo client.txt solo se usa para el cargue inicial de los registros almacenados
Tecnología

Java JDK 11
Spring boot 2.5.4 tools gradle
Libreria: lombok
Dependencias: spring web – validation

Archivos client

Se tiene client_base.txt como base de clientes de ejemplo para ser creados desde el servicio.
Client.txt -> es el archivo donde se almacena la información.

Run
Ejecutar el main que esta en el archivo BTreePlusApplication.java

EndPoint
consulta de un registro :
GET : localhost:8080/treeBPlus/123 

Insertar un cliente:
POST : localhost:8080/treeBPlus
   {
        "id": 123,
        "firstName": "steven",
        "lastName": "jimenez",
        "direction": "call 9",
        "age": 30,
        "movil": "3158411478",
        "dateBirth": "1990-12-29"
    }
]




