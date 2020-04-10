

public class Mezclador {
	
	
    public String mezcla(String A, String B){
        int hasta = A.length();
        char aux1[] = A.toCharArray();
        char aux2[] = B.toCharArray();
        int j = 0;
        int x = ((int)A.length()/B.length());
        
        System.out.println(B);
        
        if(x<2){
				A= A+A+A;						//
				x= ((int)A.length()/B.length()); //POR SI LLEGASE A HABER UNA PW MAS GRANDE QUE EL TEXTO GENERADO
			}									//O DEL MISMO TAMAÑO
        
        for (int i = 0; i < hasta; i+=x) {
                if(j<B.length()){
                aux1[i] = aux2[j];
                j = j+1;}
            
        }
        System.out.println("MEZCLANDO TEXTO.............");
						
        String mezclado = new String(aux1);
        
		
        return mezclado;
    }
}
