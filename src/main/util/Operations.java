package util;

public class Operations {

    public static boolean arraysEqual(Object[] arr1, Object[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] == null) {
                return arr2[i] == null;
            } else if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }
}
