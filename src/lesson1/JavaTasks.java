package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     *
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        Scanner inputFile;
        try {
            inputFile = new Scanner(new File(inputName));
        } catch (Exception e){
            throw new IllegalArgumentException(inputName + "Error File not found" + e);
        }
        ArrayList<Integer> time = new ArrayList<Integer>();
        try{
            FileInputStream fstream = new FileInputStream(inputName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) != null){
                if(!strLine.matches("^([0-1]\\d:[0-5]\\d:[0-5]\\d)|(2[0-4]:[0-5]\\d:[0-5]\\d)$"))
                    throw new IllegalArgumentException("error Неверный формат строки");
                String[] split = strLine.split(":");
                int numberOfSeconds = Integer.parseInt(split[0]) * 3600 + Integer.parseInt(split[1]) * 60 +
                        Integer.parseInt(split[2]);
                time.add(numberOfSeconds);
            }
        }catch (IOException e){
            System.out.println("Ошибка");
        }

        int[] arrIntTime = new int[time.size()];
        for(int i = 0; i < arrIntTime.length; i++){
            arrIntTime[i] = time.get(i).intValue();
        }

        Sorts.quickSort(arrIntTime);

        try {
            PrintWriter writer = new PrintWriter(outputName, String.valueOf(StandardCharsets.UTF_8));
            for (int i = 0; i < time.size(); i++){
                int hour = time.get(i) / 3600;
                int minutes = (time.get(i) - hour * 3600) / 60;
                int seconds = time.get(i) - hour * 3600 - minutes * 60;
                writer.println(String.format("%02d:%02d:%02d", hour, minutes, seconds));
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Файл не создан");
        }
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        Scanner inputFile;
        try {
            inputFile = new Scanner(new File(inputName));
        } catch (Exception e){
            throw new IllegalArgumentException(inputName + "Error File not found" + e);
        }

        ArrayList<Double> temp = new ArrayList<>();

        try{
            FileInputStream fstream = new FileInputStream(inputName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;

            while ((strLine = br.readLine()) != null){
                if(!strLine.matches("^(0|((-)?((\\d+)\\.\\d)))$")) {
                    throw new IllegalArgumentException("error Неверный формат строки");
                }
                temp.add(Double.parseDouble(strLine));
            }
        }catch (IOException e){
            System.out.println("Ошибка");
        }

        double[] tempDouble = new double[temp.size()];

        for (int i = 0; i < temp.size(); i++){
            tempDouble[i] = temp.get(i);
        }
        quickSort(tempDouble, 0, tempDouble.length - 1);
        try {
            PrintWriter writer = new PrintWriter(outputName, String.valueOf(StandardCharsets.UTF_8));
            for (int i = 0; i < tempDouble.length; i++){
                writer.println(tempDouble[i]);
            }
            writer.close();
        } catch (IOException e){
            System.out.println("Файл не создан");
        }
    }

    // Метод быстрой сортировки для массива double, для метода sortTemperature
    private static void quickSort(double[] arr, int low, int high) {
        if (arr == null || arr.length == 0)
            return;
        if (low >= high)
            return;

        int middle = low + (high - low) / 2;
        double pivot = arr[middle];
        int i = low, j = high;

        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
                j--;
            }
            if (i <= j) {
                double temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        if (low < j)
            quickSort(arr, low, j);
        if (high > i)
            quickSort(arr, i, high);
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
