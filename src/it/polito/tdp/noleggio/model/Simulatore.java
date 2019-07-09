package it.polito.tdp.noleggio.model;

import java.time.Duration;
import java.time.*;
import java.util.*;
import java.util.PriorityQueue;

import it.polito.tdp.noleggio.model.Evento.TipoEvento;

public class Simulatore {
	
	private PriorityQueue<Evento> queue = new PriorityQueue<>() ;
	private int numeroMacchine;
	private int autoDisponibili;
	private int nClienti;
	private int nClientiInsoddisfatti;
	
	private LocalTime oraInizio = LocalTime.of(8, 0);
	private LocalTime oraFine = LocalTime.of(20, 0);
	private Duration arrivoCliente = Duration.ofMinutes(10);
	private List<Duration> durataNoleggio;
	private Random rand = new Random();
	
	public Simulatore() { //inizializzo gli eventi
		
		durataNoleggio = new ArrayList<Duration>();
		durataNoleggio.add(Duration.ofHours(1));
		durataNoleggio.add(Duration.ofHours(2));
		durataNoleggio.add(Duration.ofHours(3));
		
	}
	
	public void init() { //inizializzo i dati del mondo e le variabili interne
		numeroMacchine = 10;
		autoDisponibili = numeroMacchine;
		nClienti = 0;
		nClientiInsoddisfatti = 0;
		queue.clear(); //pulisco la coda
		
		for(LocalTime ora = oraInizio; ora.isBefore(oraFine); ora = ora.plus(arrivoCliente)) {
			queue.add(new Evento(ora, TipoEvento.CLIENTE_ARRIVA));
		}
		
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			if(e.getTipo()==TipoEvento.CLIENTE_ARRIVA) {
				nClienti++;
				if(autoDisponibili==0) {
					nClientiInsoddisfatti++;
				}
				
				else {
					autoDisponibili --;
					LocalTime nuova = e.getTempo().plus(durataNoleggio.get(rand.nextInt(durataNoleggio.size())));
					
					queue.add(new Evento(nuova, TipoEvento.AUTO_RESTITUITA));
				}
				
			}
			
			if(e.getTipo()==TipoEvento.AUTO_RESTITUITA) {
				autoDisponibili++;
			}
		}
		
		System.out.println(nClienti+" di cui "+nClientiInsoddisfatti+" insoddisfatti!");
		
	}

}


















