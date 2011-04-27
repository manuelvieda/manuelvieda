/*  UNIVERSIDAD DE LOS ANDES - FACULTAD DE INGENIERIA
 * Departamento de Ingenieria de Sistemas y Computacion
 * 
 * Project Name: BluetoothUniandes.c
 * Created: 20/03/2011
 * Author: Manuel Vieda
 */ 

#define F_CPU 20000000UL
#define BUFF_SIZE 255
#define BUFF_MASK 0xFF

#define COD_DISP "01"


#include <stdio.h>			// Standard IO facilities 
#include <stdint.h>			// Standard Integer Types
#include <util/delay.h>		// Convenience functions for busy-wait delay loops
#include <avr/io.h>			// AVR device-specific IO definitions
#include <avr/pgmspace.h>	// Program Space Utilities
#include <avr/interrupt.h>	// Interrupts handling
#include <string.h>

#include "Includes/USART.h"

//  ---------------------------------------------------------------------------
//     GLOBAL SYSTEM VARIABLES / BUFFERS
//  ---------------------------------------------------------------------------


volatile int buffer[BUFF_SIZE];
volatile int inicioBuffer = 0x00;
volatile int finBuffer = 0x00;

volatile int lineaRecibida = 0x00;
volatile int finLineaRecibida = 0x00;


//  ---------------------------------------------------------------------------
//      DECLARATION OF METHODS / INTERNAL PROCEDURES
//  ---------------------------------------------------------------------------

void initHardware(void);
void initBluetooth(void);
int recibioOK(void);

//  ---------------------------------------------------------------------------
//      MAIN PROGRAM / ROUTINE
//  ---------------------------------------------------------------------------
int main(void)
{

	_delay_ms(200); // Power and Hardware stabilization initial delay
	initHardware();
	
	_delay_ms(200);
	initBluetooth();
	
    while(1)
    {
		if(lineaRecibida==0x01){
			
			if( (  ( (buffer[inicioBuffer]=='A') || (buffer[inicioBuffer]=='a')) &&  ( (buffer[(inicioBuffer+1)&BUFF_MASK]=='T') || (buffer[(inicioBuffer+1)&BUFF_MASK]=='t') ))){
				// AT Command recived
				inicioBuffer+=2;
				switch (buffer[inicioBuffer])
				{
					case '?':
						inicioBuffer=finLineaRecibida;
						USART_EnviarLn("OK");
						break;
					case 'I':
					case 'i':
						inicioBuffer=finLineaRecibida;
						USART_EnviarLn("1SPP Ver: 1");
						USART_EnviarLn("OK");
						break;
					case '+':
						inicioBuffer = (inicioBuffer+1)&BUFF_MASK;
						
						if( (((inicioBuffer+6)&BUFF_MASK)==finLineaRecibida) && ((buffer[(inicioBuffer+5)&BUFF_MASK])==0x0D) ){

							
							if( ( (buffer[inicioBuffer]=='B') || (buffer[inicioBuffer]=='b') ) &&
								( (buffer[(inicioBuffer+1)&BUFF_MASK]=='T') || (buffer[(inicioBuffer+1)&BUFF_MASK]=='t') ) ){
									
									// BT Command
									inicioBuffer+=2;
									if( ((buffer[inicioBuffer]=='C')   || (buffer[inicioBuffer]=='c')) &&
										((buffer[(inicioBuffer+1)&BUFF_MASK]=='O') || (buffer[(inicioBuffer+1)&BUFF_MASK]=='o')) &&
										((buffer[(inicioBuffer+2)&BUFF_MASK]=='D') || (buffer[(inicioBuffer+2)&BUFF_MASK]=='d')) ){
											USART_EnviarLn(COD_DISP);
											USART_EnviarLn("OK");										
									}
									else{
										USART_EnviarLn("ERROR +BT* COMMAND NOT FOUND");;
									}
								}else{
									
									USART_EnviarLn("ERROR - NO +BT CODE");
								}
							
															
						}else{
							USART_EnviarLn("ERROR COMMAND +");
						}
						inicioBuffer=finLineaRecibida;
					
						// TODO
						break;
						
					default:
						USART_EnviarLn("ERROR at");
						// Enviamos que se recibio, solo para TESTING!
						while(inicioBuffer!=finBuffer){
							//TODO
							USART_Enviar(buffer[inicioBuffer++]);
							inicioBuffer &= BUFF_MASK;
						}
						break;
						
				}
				
			}
			else {
				
				if( ((buffer[(inicioBuffer)]=='C')||(buffer[(inicioBuffer)&BUFF_MASK]=='c')) &&
					((buffer[(inicioBuffer+1)]=='L')||(buffer[(inicioBuffer+1)&BUFF_MASK]=='l')) &&
					((buffer[(inicioBuffer+2)]=='O')||(buffer[(inicioBuffer+2)&BUFF_MASK]=='o')) &&
					((buffer[(inicioBuffer+3)]=='S')||(buffer[(inicioBuffer+3)&BUFF_MASK]=='s')) &&
					((buffer[(inicioBuffer+4)]=='E')||(buffer[(inicioBuffer+4)&BUFF_MASK]=='e')) ){
						
						//Finaliza la conexion actual
						USART_EnviarLn("+++");
						while (lineaRecibida!=0x01){}
						inicioBuffer=0;
						finBuffer=0;
						lineaRecibida=0x00;
						initBluetooth();
					}
					else{
						USART_EnviarLn("ERROR");
				
						// Enviamos que se recibio, solo para TESTING!
						while(inicioBuffer!=finBuffer){
							//TODO
							USART_Enviar(buffer[inicioBuffer++]);
							inicioBuffer &= BUFF_MASK;
						}
					}		
			}
			
			lineaRecibida=0x00;
		}
	}
	
	return 0;
}


//  ---------------------------------------------------------------------------
//      IMPLEMENTATION OF METHODS / ROUTINES / PROCEDURES
//  ---------------------------------------------------------------------------


/**
 * This method handles the initilization of the project's hardware. Within the
 * hardware are both external and internal peripherals of the microcontroller
 *
 * @PARAM:      None
 * @PRE:        None
 * @POST:       The hardware (Modules and Peripherals) is initialized and is
 *                      ready for use.
 */
void initHardware(void){
	
	USART_init();
	USART_EnableTx();
	USART_EnableRx();
	sei();
	
}


/**
 *
 */
void initBluetooth(void){
	
	USART_EnviarLn("+++");
	_delay_ms(200);
	inicioBuffer=0;
	finBuffer=0;
	lineaRecibida=0;
	
	USART_EnviarLn("AT+BTSRV=1, \"SerialPort Loc\", 000000000000, 0");
	while(lineaRecibida!=0x01){ }
		
		if(recibioOK()==0x01){}
		else{
			// Volvemos a intentar activar el servicio
			initBluetooth();
		}
	
	
}

/**
 * Este metodo verifica si se recibio un mensaje <CR><LF>OK<CR><LF> de confirmacion
 */

int recibioOK(void){
	_delay_ms(50);
	


	if(buffer[inicioBuffer]==0x0D){
		if(buffer[(inicioBuffer+1)&BUFF_MASK]==0x0A){
			if( (buffer[((inicioBuffer+2)&BUFF_MASK)]=='O') &&
				(buffer[((inicioBuffer+3)&BUFF_MASK)]=='K') &&
				(buffer[((inicioBuffer+4)&BUFF_MASK)]==0x0D) &&
				(buffer[((inicioBuffer+5)&BUFF_MASK)]==0x0A)){
					inicioBuffer=(finLineaRecibida+1)&BUFF_MASK;
					lineaRecibida=0x00;
					return 1;
				}
		}
	}
	inicioBuffer=(finLineaRecibida+1)&BUFF_MASK;
	lineaRecibida=0x00;
	return 0;	
}


//  ---------------------------------------------------------------------------
//   ATENCION DE INTERRUPCION USART
//  ---------------------------------------------------------------------------

ISR(USART0_RX_vect){
	
	if(inicioBuffer!=(finBuffer+1)){
		
		// Obtenemos datos de USART
		while ( !(UCSR0A & (1<<RXC0)) );
		uint8_t dato = UDR0;
		
		// Almacenamos en Buffer FIFO
		buffer[finBuffer++]=dato;
		finBuffer &= BUFF_MASK;
		
		// Verificamos si se ha recibido una linea
		if(dato==0x0D){ lineaRecibida=0x01; finLineaRecibida=finBuffer; }
	}
}



ISR(USART0_TX_vect){
	
}
