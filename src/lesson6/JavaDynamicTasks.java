package lesson6;

import kotlin.NotImplementedError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.Math;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     *
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
//    О(n*m)
    public static String longestCommonSubSequence(String first, String second) {
        char[] firstChar = first.toCharArray();
        char[] secondChar = second.toCharArray();
        int firstLen = firstChar.length;
        int secondLen = secondChar.length;
        int[][] matrix = new int[firstLen + 1][secondLen + 1];

        for (int i = 0; i <= firstLen; i++) {
            matrix[i][0] = 0;
        }
        for (int i = 0; i <= secondLen; i++) {
            matrix[0][i] = 0;
        }

        for (int i = 1; i <= firstLen; i++) {
            for (int j = 1; j <= secondLen; j++) {
                if (firstChar[i - 1] == secondChar[j - 1]) {
                    matrix[i][j] = matrix[i - 1][j - 1] + 1;
                } else {
                    matrix[i][j] = Math.max(matrix[i - 1][j], matrix[i][j - 1]);
                }
            }
        }

        int i = firstLen;
        int j = secondLen;
        StringBuilder result = new StringBuilder();

        while ((i > 0) && (j > 0)) {
            if (firstChar[i - 1] == secondChar[j - 1]) {
                result.append(firstChar[i - 1]);
                i--;
                j--;
            } else {
                if (matrix[i][j - 1] > matrix[i - 1][j]) {
                    j--;
                } else {
                    i--;
                }
            }
        }
        return result.reverse().toString();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     *
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */

    //O(n^2)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {
        if(list.isEmpty()) return list;

        int listSize = list.size();
        int[] maxForEach = new int[listSize];
        int[] maxLen = new int[listSize];

        for (int i = 0; i < listSize; i++){
            maxLen[i] = 1 ;
            maxForEach[i] = -1;
                for (int j = 0; j < i; j++){
                    if (list.get(j) < list.get(i) && maxLen[j] + 1 > maxLen[i]){
                          maxLen[i] = maxLen[j] + 1;
                          maxForEach[i] = j;
                        }
                }
        }

        int ans = maxLen[0];
        int pos = 0;

        for (int i = 0; i < listSize; i++)
            if (maxLen[i] > ans){
                ans = maxLen[i];
                pos = i;
            }

        ArrayList<Integer> result = new ArrayList<>();

        while (pos != -1){
            result.add(list.get(pos));
            pos = maxForEach[pos];
        }

        Collections.reverse(result);
        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     *
     * В файле с именем inputName задано прямоугольное поле:
     *
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     *
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     *
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    public static int shortestPathOnField(String inputName) {
        throw new NotImplementedError();
    }

    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}
