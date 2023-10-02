import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class UserData {
    public static void main(String[] args) {
        getData();
        dataWriting(familyName, name, patronymic,
                birthDate, phone, gender);
    }

    static String familyName;
    static String name;
    static String patronymic;
    static String birthDate;
    static BigInteger phone;
    static Character gender;

    static String[] data;
    static Scanner scanner = new Scanner(System.in);


    //Метод для получения данных от пользователя
    public static void getData(){
        System.out.println("Введите фамилию, имя, отчество, дату рождения, номер телефона, пол через пробел:");
        int parametersAmount = 6;
        while (true) {
            familyName = null;
            name = null;
            patronymic = null;
            birthDate = null;
            phone = null;
            gender =null;
            data = scanner.nextLine().split(" ");
            try {
                if (data.length > parametersAmount) {   //параметров не должно быть больше, чем требуется
                    throw new ParametersAmountException1();
                }
                if (data.length < parametersAmount) {   //параметров не должно быть меньше, чем требуется
                    throw new ParametersAmountException2();
                }
                dataParsing(data);
                if (familyName != null && name != null && patronymic != null &&
                        birthDate != null && phone != null && gender != null) {
                    System.out.println("Данные введены корректно.");
                    break;
                }
            } catch (ParametersAmountException1 e) {
                System.out.println("Введено больше параметров, чем требуется. Попробуйте снова:");
            } catch (ParametersAmountException2 e) {
                System.out.println("Введено меньше параметров, чем требуется. Попробуйте снова:");
            }
        }
    }


    //Метод для парсинга данных
    public static void dataParsing(String[] array){
        try {
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals("f") || array[i].equals("m")) {  //проверка значения пола на f/m
                    gender = array[i].charAt(0);
                    array[i] = "";
                }
            }
            if (gender == null)
                throw new GenderValueException();
        } catch (GenderValueException e) {
            System.out.println("Значение пола должно быть равно f или m. Попробуйте снова:");
        }

        try {
            for (int i = 0; i < array.length; i++) {
                if (array[i].matches("[0-9]+")) {    //проверка номера телефона на содержание одних цифр
                    phone = new BigInteger(array[i]);
                    array[i] = "";
                }
            }
            if (phone == null)
                throw new PhoneValueException();
        } catch (PhoneValueException e) {
            System.out.println("Номер телефона должен быть в формате 79871234567. Попробуйте снова:");
        }

        try {
            for (int i = 0; i < array.length; i++) {
                if (array[i].matches("\\d\\d.\\d\\d.\\d\\d\\d\\d")) {    //проверка формата даты рождения
                    birthDate = array[i];
                    array[i] = "";
                }
            }
            if (birthDate == null)
                throw new BirthDateValueException1();
            if (Integer.parseInt(birthDate.substring(0, 2)) > 31 ||     //проверка правильности даты рождения
                    Integer.parseInt(birthDate.substring(3, 5)) > 12 ||
                    Integer.parseInt(birthDate.substring(6, 10)) > 2020) {
                birthDate = null;
                throw new BirthDateValueException2();
            }
            if (Integer.parseInt(birthDate.substring(0, 2)) < 1 ||
                    Integer.parseInt(birthDate.substring(3, 5)) < 1 ||
                    Integer.parseInt(birthDate.substring(6, 10)) < 1900) {
                birthDate = null;
                throw new BirthDateValueException2();
            }
        } catch (BirthDateValueException1 e) {
            System.out.println("Дата рождения должна быть в формате dd.mm.yyyy. Попробуйте снова:");
        } catch (BirthDateValueException2 e) {
            System.out.println("Дата рождения должна быть реально существующей датой. Попробуйте снова:");
        }

        try {
            for (int i = 0; i < array.length; i++) {    //проверка фамилии на содержание только букв
                if (array[i] != "" && (array[i].matches("^[a-zA-Z]*$") || array[i].matches("^[а-яА-Я]*$"))) {
                    familyName = array[i];
                    array[i] = "";
                    break;
                }
            }
            for (int j = 0; j < array.length; j++) {    //проверка имени на содержание только букв
                if (array[j] != "" && (array[j].matches("^[a-zA-Z]*$") || array[j].matches("^[а-яА-Я]*$"))) {
                    name = array[j];
                    array[j] = "";
                    break;
                }
            }
            for (int k = 0; k < array.length; k++) {    //проверка отчества на содержание только букв
                if (array[k] != "" && (array[k].matches("^[a-zA-Z]*$") || array[k].matches("^[а-яА-Я]*$"))) {
                    patronymic = array[k];
                    array[k] = "";
                    break;
                }
            }
            if (familyName == null || name == null || patronymic == null)
                throw new NameValueException();
        } catch (NameValueException e) {
            System.out.println("ФИО должно быть в формате: Фамилия Имя Отчество. Попробуйте снова:");
        }
    }



    //метод для записи данных в файл
    public static void dataWriting(String familyName, String name, String patronymic,
                                   String birthDate, BigInteger phone, Character gender){
        String fileName = String.format("%s.txt", familyName);
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            fileWriter.write(String.format("<%s><%s><%s><%s><%d><%c>\n",
                    familyName, name, patronymic, birthDate, phone, gender));
            System.out.println("Запись в файл успешно завершена.");
        } catch(IOException e){
            System.out.println("Ошибка записи в файл.");
            e.printStackTrace();
        }
    }
}

abstract class DataException extends Exception{
    public DataException() {
    }
}

class ParametersAmountException1 extends DataException{

    public ParametersAmountException1() {
    }
}

class ParametersAmountException2 extends DataException{

    public ParametersAmountException2() {
    }
}

class GenderValueException extends DataException{
    public GenderValueException() {
    }
}

class PhoneValueException extends DataException{
    public PhoneValueException() {
    }
}

class BirthDateValueException1 extends DataException{
    public BirthDateValueException1() {
    }
}

class BirthDateValueException2 extends DataException{
    public BirthDateValueException2() {
    }
}

class NameValueException extends DataException{
    public NameValueException() {
    }
}

/*
Тестирование:
Иванов Иван Иванович 01.01.2001 79871234567 m - успешно
m Иванов 79871234567 Иван 01.01.2001 Иванович - успешно
Иванов Иван Иванович 01.01.2001 79871234567 m Москва - ParametersAmountException1
Иванов Иван Иванович 01.01.2001 79871234567 - ParametersAmountException2
Иванов Иван Иванович 01.01.2001 79871234567 муж - GenderValueException
Иванов Иван Иванович 01.01.2001 +7(987)1234567 m - PhoneValueException
Иванов Иван Иванович 1января2001года 79871234567 m - BirthDateValueException1
Иванов Иван Иванович 32.01.2001 79871234567 m - BirthDateValueException2
Иванов Иван Иванович 01.13.2001 79871234567 m - BirthDateValueException2
Иванов Иван Иванович 01.01.2024 79871234567 m - BirthDateValueException2
Иванов Иван Иванович 00.01.2001 79871234567 m - BirthDateValueException2
Иванов Иван Иванович 01.00.2001 79871234567 m - BirthDateValueException2
Иванов Иван Иванович 01.01.0001 79871234567 m - BirthDateValueException2
Иванов% Иван Иванович 01.01.2001 79871234567 m - NameValueException
Иванов Иван№ Иванович 01.01.2001 79871234567 m - NameValueException
Иванов Иван 9Иванович 01.01.2001 79871234567 m - NameValueException
Ivanov Ivan Ivanovich 01.01.2001 79871234567 m - успешно
 */
