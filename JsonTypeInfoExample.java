import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;

public class JsonTypeInfoExample {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        A a1 = new A(1.0f, new B(1));
        A a2 = new A(2.0f, new C("c"));

        System.out.println(a1);
        System.out.println(a2);
        System.out.println();

        String serializedClassA1 = objectMapper.writeValueAsString(a1);
        String serializedClassA2 = objectMapper.writeValueAsString(a2);

        System.out.println(serializedClassA1);
        System.out.println(serializedClassA2);
        System.out.println();

        A deSerializedClassA1 = objectMapper.readValue(serializedClassA1, A.class);
        A deSerializedClassA2 = objectMapper.readValue(serializedClassA2, A.class);

        System.out.println(deSerializedClassA1);
        System.out.println(deSerializedClassA2);
        System.out.println();

        System.out.println(deSerializedClassA1.getT() instanceof B);
        System.out.println(deSerializedClassA2.getT() instanceof C);
        System.out.println();
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class A<T> {
        private float a;
        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
        private T t;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class B {
        private int b;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class C {
        private String c;
    }
}
