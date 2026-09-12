## Code review manual
Parte 3## Code review manual


Archivo	Hallazgo	Tipo	Severidad	Propuesta

ParkingFeeCalculator.java	Los valores 15, 60, 80, 150 y 20 aparecen directamente en el código sin ningún nombre que explique qué representan.	Maintainability	Media	Definir constantes como FREE_MINUTES_LIMIT = 15, MAX_NORMAL_FEE = 80, LOST_TICKET_FEE = 150 para que el significado sea explícito y sea más fácil cambiar las reglas de negocio a futuro.

ParkingFeeCalculator.java	La validación de lostTicket ocurre antes que la validación de minutos negativos, así que un boleto perdido con minutos negativos nunca lanza la excepción esperada.	Bug	Media	Decidir si ese orden es intencional; si no lo es, validar primero los minutos negativos independientemente de lostTicket, y documentarlo con un comentario si se deja así a propósito.

ParkingFeeCalculatorTest.java	Solo la primera prueba (fifteenMinutesShouldBeFree) tiene los comentarios explícitos de Arrange/Act/Assert; el resto sigue la misma estructura pero sin marcarla.	Readability	Baja	Agregar los comentarios // Arrange, // Act, // Assert de forma consistente en todas las pruebas.

ParkingFeeCalculatorTest.java	No existe una prueba justo en el minuto 120 (el último minuto de la primera hora adicional); ya se prueba 121, pero falta el límite inferior de esa misma región.	Testing	Baja	Agregar una prueba con exactamente 120 minutos para confirmar que el redondeo hacia arriba no se activa un minuto antes de tiempo.


Parte 4

LegacyParkingReceipt.java	La línea if (plate == "") compara Strings con == en vez de .equals() o .isEmpty(), lo cual puede dar resultados inesperados en Java.	Bug	Alta	Cambiar a plate.isEmpty() o plate.equals("").

LegacyParkingReceipt.java	La línea boolean free = fee == 0 ? true : false; usa un operador ternario innecesario, ya que fee == 0 ya es un valor booleano por sí mismo.	Readability	Baja	Simplificar a boolean free = fee == 0;.

LegacyParkingReceipt.java	La condición if (free == true) es redundante; comparar una variable booleana contra true no agrega información.	Readability	Baja	Simplificar a if (free) .

LegacyParkingReceipt.java	El método imprime directamente a consola con System.out.println dentro de la lógica de negocio, mezclando la construcción del recibo con el registro de eventos.	Design	Media	Usar un logger apropiado (ej. SLF4J) o quitar la línea si no es necesaria, separando responsabilidades.


Parte 8 ## Análisis con SonarQube for IDE


Archivo/línea	Regla o mensaje	Explicación	¿Estoy de acuerdo?


LegacyParkingReceipt.java, línea 26	Replace this use of System.out by a logger (java:S106)	Imprimir directamente a consola con System.out.println dentro de la lógica de negocio mezcla dos responsabilidades distintas (calcular/construir el recibo, y registrar un evento).	Sí, porque un logger permite controlar niveles de detalle sin modificar el código, algo que System.out no permite.


LegacyParkingReceipt.java, línea 28	Remove the unnecessary boolean literals (java:S1125)	La línea boolean free = fee == 0 ? true : false; usa un operador ternario innecesario, porque fee == 0 ya es en sí mismo un valor booleano.	Sí, es una simplificación directa que no cambia el comportamiento del programa.


LegacyParkingReceipt.java, línea 30	Remove the unnecessary boolean literal (java:S1125)	La condición if (free == true) compara una variable booleana contra true de forma  redundante; basta con if (free).	Sí, por la misma razón que el hallazgo anterior.



PREGUNTAS 1-8

P1

Probar muchos valores dentro de la misma región no aporta mucho porque todos esos valores activan la misma rama de código y el mismo resultado esperado. Por ejemplo, probar con 20, 30 y 45 minutos siempre cae en la misma regla de $20 fijo, así que la segunda y tercera prueba no agrega información nueva sobre el comportamiento del programa.


P2

Identifiqué dos fronteras importantes: 15/16 minutos, donde se pasa de ser gratis a cobrar $20, y 60/61 minutos, donde termina la tarifa fija y empieza a cobrarse por hora adicional. Vale la pena probar cerca de ellas porque ahí es donde más comúnmente ocurren errores de lógica de "uno de más o uno de menos" (off-by-one), por ejemplo usar < en vez de <= en una condición.
P3


No. Que todas las pruebas estén en verde solo demuestra que el programa se comporta como se esperaba para los casos que decidimos probar. Si existe algún escenario que nadie pensó en cubrir, el programa puede fallar ahí aunque todas las pruebas existentes sigan pasando.


P4
Sonar detectó que el texto "ERROR" se repite 4 veces en el código de LegacyParkingReceipt.java, sugiriendo definirlo una sola vez como una constante. Yo no había anotado esta observación en mi code review manual.


P5
Yo identifiqué que la validación de lostTicket ocurre antes que la validación de minutos negativos en ParkingFeeCalculator, lo cual es un problema de lógica de negocio que Sonar no reportó, porque Sonar no entiende las reglas reales del estacionamiento, solo analiza patrones de código.


P6
No, no todos los hallazgos de Sonar tienen la misma importancia. Por ejemplo, comparar Strings con == puede causar un bug real en ciertos casos (severidad más alta), mientras que quitar un booleano innecesario como en if (free == true) es solo un tema de estilo que no cambia el comportamiento del programa (severidad baja).


P7
No, Sonar analiza cómo está escrito el código (sintaxis, patrones, buenas prácticas), pero no sabe nada sobre las reglas reales del negocio del estacionamiento. El código podría estar perfectamente escrito según Sonar y aun así cobrar mal si alguien programó una regla de negocio incorrecta desde el inicio, como el rango de $20 de 16 a 60 minutos.


P8
a) Las pruebas unitarias verifican que el programa se comporte como se espera al ejecutarlo con datos reales; Sonar no ejecuta el código, solo lo analiza de forma estática, así que puede pasar por alto errores de lógica que solo aparecen al correr el programa. 
b) El code review humano aporta juicio sobre el negocio, claridad para otras personas del equipo, y decisiones de diseño que ninguna herramienta automática puede evaluar por sí sola.
