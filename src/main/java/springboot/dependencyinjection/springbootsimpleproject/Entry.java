package springboot.dependencyinjection.springbootsimpleproject;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Entry {
    private String originalWord;
    private String translatedWord;

}
