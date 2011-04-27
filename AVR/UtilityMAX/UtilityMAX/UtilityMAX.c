/*
 * UNIVERSIDAD DE LOS ANDES - FACULTAD DE INGENIERIA
 * DEPARTAMENTO DE INGENIERIA DE SISTEMAS Y COMPUTACIÓN
 *
 * FILE:	 UtilityMAX.c
 * CREATED:	 26/04/2011
 * MODIFIED: 26/04/2011
 * AUTHOR:	 Manuel Vieda <http://manuelvieda.com>
 */ 

#define F_CPU 20000000UL


#include <stdio.h>			// Standard IO facilities 
#include <stdint.h>			// Standard Integer Types
#include <avr/io.h>			// AVR device-specific IO definitions
#include <avr/interrupt.h>	// Interrupts handling
#include <util/delay.h>		// Convenience functions for busy-wait delay loops

#include "includes/USART.h"
#include "includes/ADC.h"

//  ---------------------------------------------------------------------------
//     GLOBAL SYSTEM VARIABLES / BUFFERS
//  ---------------------------------------------------------------------------

//  ---------------------------------------------------------------------------
//      DECLARATION OF METHODS / INTERNAL PROCEDURES
//  ---------------------------------------------------------------------------

void initHardware(void);

//  ---------------------------------------------------------------------------
//      MAIN PROGRAM / ROUTINE
//  ---------------------------------------------------------------------------

int main(void)
{
	_delay_ms(200); // Power and Hardware initial stabilization 
	initHardware();

    while(1)
    {
		_delay_ms(250);
		_delay_ms(250);
		//USART_EnviarLn("- ");
		_delay_ms(250);
		_delay_ms(250);
		
		uint32_t resADC = ADC_RealizarConversion(0);
		
		int8_t resp[3];

		resp[2] = resADC/100;
		resp[1] = resADC/10-resp[2]*10;
		resp[0] = resADC-resp[2]*100-resp[1]*10;
	
		USART_Enviar(' ');
		USART_Enviar(resp[2]+0x30);
		USART_Enviar(resp[1]+0x30);
		USART_Enviar(resp[0]+0x30);
		USART_Enviar(0x0D);
        //TODO:: Please write your application code 
    }
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
	
	// Desactivamos las interrupciones
	cli();
	
	// Inicializamos la USART y habilitamos para transmisión y recepción
	USART_init();
	USART_EnableTx();
	USART_EnableRx();
	
	// Inicializamos y habilitamos el conversor Análogo-Digital
	ADC_Init();
	ADC_Enable();
	
	sei();
}


//  ---------------------------------------------------------------------------
//   ATENCION DE INTERRUPCION USART
//  ---------------------------------------------------------------------------

ISR(USART_RX_vect){

}

ISR(USART_TX_vect){
	
}