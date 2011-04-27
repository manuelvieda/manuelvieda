/**
 * -------------------------------------------------------------------
 * Copyright (c) 2010 - Todos los derechos reservados
 * Manuel Eduardo Vieda Salomon <mail@manuelvieda.com>
 * Ingeniero Electronico, Ingeniero de Sistemas y Computacion
 * Universidad de los Andes. Bogota, Colombia.
 * -------------------------------------------------------------------
 *
 * Archivo:				USART.c
 * Fecha Creacion:		Noviembre 21 de 2010
 * Fecha Modificacion:	Noviembre 21 de 2010
 * Version (Revision):	0.1 (1)
 *
 * Descripcion:	Libreria que facilita el uso del modulo de conversion
 *              analogo a digital de los microcontroladores: ATmega 48/88/168/644
 *
 *
 * --------------------------------------------------------------------
 * LICENCIA:
 * 
 * Se encuentra totalmente permitido el uso y distribucion en forma de codigo y
 * binario bajo la condicion de cumplir con las siguientes condiciones de uso:
 *
 *   - Toda redistribucion del codigo fuente debe mantener sin ningun cambio la
 *     presente notificacion de copyright, la lista de condiciones y la descripcion
 *     del archivo.
 *
 *   - Ni el nombre del poseedor del presente copyright o el nombre
 *     de cualquier colaborador puede ser usado para la promocion de productos
 *     derivados de este programa/codigo sin una autorizacion escrita y especifica
 *     por parte del autor.
 *
 *   - Si considera que este contenido le ha sido bastante util, puedes invitarme
 *     a una ronda de cervezas. (The Beer-Ware License, Revision 42)
 * ---------------------------------------------------------------------
 */


 #include <stdint.h>
 #include <avr/io.h>
 #include "USART.h"

 /**
 * ADC_init
 * Este metodo se encarga de realizar la unicializacion del modulo ADC del microcontrolador.
 * 
 *
 * @PARAMS: Ninguno
 * @PRE:    Los parametros se encuentran bien definidos
 * @POST:   Se inicializa el modulo de conversion analogo-digital (ADC), quedando
 *          listo para realizar la conversion de datos. 
 * @RETURN: Void
 */
void ADC_Init(){

	// Nos aseguramos que el modulo se encuentre en un estado sin configuracion
	ADMUX  = 0x00;
	ADCSRA = 0x00;
	ADCSRB = 0x00;
	DIDR0  = 0x00;

		// Clock Prescale
	ADCSRA |= _BV(ADPS2)|_BV(ADPS1)|_BV(ADPS0);


	/*  Seleccionamos la fuente de referencia
	    00 - AREF, Internal Vref Off
		01 - AVCC with External capacitor at AREF pin
		10 - Internal 1.10V Voltage reference, External capacitor at AREF pin
		11 - Internal 2.53V Voltage reference, External capacitor at AREF pin
	*/
	ADMUX |= _BV(REFS0);

	// ADC Left Adjust Result
	ADMUX |= _BV(ADLAR);

	// Enable Intrrrupts
	//ADCSRA |= _BV(ADIE);

}

void ADC_Enable(){
	ADCSRA |= _BV(ADEN);
}

void ADC_Disable(){
	ADCSRA &= ~(_BV(ADEN));
}


uint32_t ADC_RealizarConversion(uint8_t ch){

	// Seleccionamos el canal donde se realiza la conversion
	ADMUX &= 0xE0;
	ADMUX |= ch;

	// Iniciamos conversion
	ADCSRA |= _BV(ADSC);

	
	// Esperamos que termine la conversion
	while(!(ADCSRA & _BV(ADIF))){};
	ADCSRA |= _BV(ADIF);

	// Almacenamos el dato en una variable
	uint32_t resADC = 0 | ADCH;
	resADC = resADC*500;
	resADC = resADC/256;
	resADC = resADC+10;

	return resADC;

}

