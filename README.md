# money-transfer-app

1: EmailNotificationService it is declared with @Service annotation to create the bean.

2: interface of Account service is required to create and then implementation class is created.

3: @RestControllerAdvice and @ExceptionHandler is used to handle the exception and give the proper error message.

4: AccountsControllerTest the AccountsService declared with @mock as we are using it for mock test.

5: if the reentrantlock is declared in the account domain then the concurrency will be improved.
