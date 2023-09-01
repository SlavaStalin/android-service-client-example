package org.acestream.engine.client.example;

import android.os.Build;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AcestreamFilters {
    private AcestreamElement[] acestreamElements;

    public AcestreamFilters(  AcestreamElement[] acestreamElements){
        this.acestreamElements = acestreamElements;
    }
    private boolean conditionMovistar(AcestreamElement element){
        String nombre = element.getNombre();
        if (nombre != null && !nombre.contains("DAZN")) {
            // Verificar que el texto contiene la palabra "liga" o la letra '#'
            Pattern pattern = Pattern.compile(".*(liga|#|M).*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nombre);
            return matcher.find();
        }
        return false;
    }
    private boolean conditionDAZN(AcestreamElement element){
        String nombre = element.getNombre();
        if (nombre != null) {
            Pattern pattern = Pattern.compile(".*(DAZN).*", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(nombre);
            return matcher.find();
        }
        return false;
    }
    private boolean conditionLength(AcestreamElement element){
        String nombre = element.getNombre();
        return nombre.length() < 20;
    }
    public AcestreamElement[] filterLength(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<AcestreamElement> filteredList = Arrays.stream(acestreamElements).filter(acestreamElement -> conditionLength(acestreamElement)).collect(Collectors.toList());
            return (AcestreamElement[]) filteredList.toArray(new AcestreamElement[0]);
        }
        return null;

    }

    public AcestreamElement[] filterMovistar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<AcestreamElement> filteredList = Arrays.stream(acestreamElements).filter(acestreamElement -> conditionLength(acestreamElement) && conditionMovistar(acestreamElement)).collect(Collectors.toList());
            return (AcestreamElement[]) filteredList.toArray(new AcestreamElement[0]);
        }
        return null;

    }

    public AcestreamElement[] filterDAZN(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            List<AcestreamElement> filteredList = Arrays.stream(acestreamElements).filter(acestreamElement -> conditionLength(acestreamElement) && conditionDAZN(acestreamElement)).collect(Collectors.toList());
            return (AcestreamElement[]) filteredList.toArray(new AcestreamElement[0]);
        }
        return null;

    }
}
