#Ejercicio 1
Aquí está el proyecto completo del ejercicio 1. Tiene dos clases (y consecuentemente dos archivos Java):
- App.java es el ejercicio sin recursividad. Simplemente usa Files.list y espera un argumento para limitar el resultado. **Si el argumento es 0, no tendrá límites y mostrará todo el contenido de la carpeta del usuario.**
- AppRecur.java es el ejercicio con recursividad. Usa Files.Walk y espera dos argumentos: el primero para limitar los resultados y el segundo para limitar la cantidad de carpetas en las que entrará. En este caso, 0 significaría
que no entre ni en la primera carpeta, así que el segundo argumento debe ser mayor que 1.
Un ejemplo de uso sería AppRecur.java 50 2, que devolverá 50 resultados y entrará a dos niveles de profundidad.
</br>
__IMPORTANTE: Files.walk tiene un error de hace tiempo que provoca AccessDeniedExceptions incluso si están controladas.__
