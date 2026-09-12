## Code review manual
Parte 3
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


Parte 8
Archivo/línea	Regla o mensaje	Explicación	¿Estoy de acuerdo?
LegacyParkingReceipt.java, línea 14	Strings and Boxed types should be compared using "equals()" (java:S4973)	La comparación plate == "" usa el operador ==, que en Java compara si dos variables apuntan al mismo objeto en memoria, no si su contenido es igual. Debería usarse .equals() o .isEmpty().	Sí, porque este mismo problema ya lo había identificado yo en mi code review manual, y coincide con cómo funciona realmente Java.
LegacyParkingReceipt.java, línea 26	Replace this use of System.out by a logger (java:S106)	Imprimir directamente a consola con System.out.println dentro de la lógica de negocio mezcla dos responsabilidades distintas (calcular/construir el recibo, y registrar un evento).	Sí, porque un logger permite controlar niveles de detalle sin modificar el código, algo que System.out no permite.
LegacyParkingReceipt.java, línea 28	Remove the unnecessary boolean literals (java:S1125)	La línea boolean free = fee == 0 ? true : false; usa un operador ternario innecesario, porque fee == 0 ya es en sí mismo un valor booleano.	Sí, es una simplificación directa que no cambia el comportamiento del programa.
LegacyParkingReceipt.java, línea 30	Remove the unnecessary boolean literal (java:S1125)	La condición if (free == true) compara una variable booleana contra true de forma  redundante; basta con if (free).	Sí, por la misma razón que el hallazgo anterior.