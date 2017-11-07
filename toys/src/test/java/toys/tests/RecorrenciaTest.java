package toys.tests;

import java.util.Calendar;

import toys.beans.Recorrencia;

public class RecorrenciaTest {

	public static void main(String[] args) {
		Recorrencia recorrencia = new Recorrencia();
		
		recorrencia.setTipo(Recorrencia.Tipo.DIARIA);
		recorrencia.setIntervalo(3);
		showRecurrence(recorrencia);
		
		recorrencia.setTipo(Recorrencia.Tipo.SEMANAL);
		recorrencia.setIntervalo(2);
		recorrencia.diaSemana[3] = true;
		recorrencia.diaSemana[5] = true;
		showRecurrence(recorrencia);
		
		recorrencia.setTipo(Recorrencia.Tipo.MENSAL);
		recorrencia.setIntervalo(2);
		recorrencia.setSemanaDia(1, 3);
		showRecurrence(recorrencia);
		
		recorrencia.setIntervalo(3);
		recorrencia.setDiaDoMes(23);
		showRecurrence(recorrencia);
		
		recorrencia.setTipo(Recorrencia.Tipo.ANUAL);
		recorrencia.setIntervalo(2);
		recorrencia.setMesSemanaDia(3, 3, 5);
		showRecurrence(recorrencia);

		recorrencia.setIntervalo(2);
		recorrencia.setMesDia(8, 27);
		showRecurrence(recorrencia);
		
		recorrencia.usarIntervaloExato = true;
		
		System.out.println("========= Intervalo exato =========");

		recorrencia.setTipo(Recorrencia.Tipo.SEMANAL);
		recorrencia.setIntervalo(2);
		recorrencia.diaSemana[3] = true;
		recorrencia.diaSemana[5] = true;
		showRecurrence(recorrencia);
		
		recorrencia.setTipo(Recorrencia.Tipo.MENSAL);
		recorrencia.setIntervalo(2);
		recorrencia.setSemanaDia(1, 3);
		showRecurrence(recorrencia);
		
		recorrencia.setIntervalo(3);
		recorrencia.setDiaDoMes(23);
		showRecurrence(recorrencia);
		
		recorrencia.setTipo(Recorrencia.Tipo.ANUAL);
		recorrencia.setIntervalo(2);
		recorrencia.setMesSemanaDia(3, 3, 5);
		showRecurrence(recorrencia);
}
	
	private static void showRecurrence(Recorrencia recurrence) {
		System.out.println(recurrence);
		System.out.println("-----------------------------------------");
		Calendar c = Calendar.getInstance();
		for (int i = 0; i < 5; i++) {
			recurrence.setProximaOcorrencia(c);
			System.out.println(c.getTime());
		}
		System.out.println("-----------------------------------------");
	}

}
