/**
 * -------------------------------------------------------------------
 * Copyright (c) 2010 - All rights reserved
 * Manuel Eduardo Vieda Salomon <mail@manuelvieda.com>
 * Electronic Engineering, Computer Science Engineer
 * University of Los Andes. Bogotá, Colombia.
 * -------------------------------------------------------------------
 * 
 * FILE:		USART.h
 * CREATED:		31/03/2011
 * MODIFIED:	31/03/2011
 * AUTHOR:		Manuel Vieda <http://manuelvieda.com>
 */ 


 #include <stdint.h>
 #include <avr/io.h>
 #include "USART.h"
 

 /**
 * USAR_init
 * Este metodo se encarga de realizar la unicializacion del modulo USART que entra por
 * parametro y en el modo selecccionado (Sincrono o asincrono), configurando la velocidad
 * y demás parámetros con la definicion de las constantes en el archivo de definiciones
 *
 * @PARAMS: Ninguno
 * @PRE:    Los parametros se encuentran bien definidos
 * @POST:   Se inicializa el puerto USART seleccionado en el modo seleccionado, quedando
 *          listo para enviar y recibir datos.
 * @RETURN: Void
 */
void USART_init(){

        // Nos aseguramos que la USART se encuentre inactiva y configuracion default
        UCSR0A = 0x20;
        UCSR0B = 0x00;
        UCSR0C = 0x06;
        

        // Asignamos el valor del valor de la tasa de transmision en baudios
        UBRR0L=UBRRVAL;                  //Parte baja del byte
        UBRR0H=(UBRRVAL>>8);     //Parte alta del byte

        if(ASY_SYN){
                //Configurado en modo Asincrono
                if(SPEED_2X){UCSR0A |= _BV(U2X0);}
                
        }else{
                // Configurado en modo Sincrono
                //UCSRC |= (1<<6);
                UCSR0C |= _BV(UMSEL00);            
        }

        switch(CHAR_SIZE){
                case 5: UCSR0C &= ~(_BV(UCSZ00)|_BV(UCSZ01));break;
                case 6: UCSR0C &= ~(_BV(UCSZ01));break;
                case 7: UCSR0C &= ~(_BV(UCSZ00));break;
                case 9: UCSR0B|= _BV(UCSZ02);break;
                default:break;
        }

        UCSR0B |= (INT_TX_COMPLETA)?_BV(TXCIE0):0;
        UCSR0B |= (INT_RX_COMPLETA)?_BV(RXCIE0):0;

        switch(CONTROL_PARIEDAD){
                case 1: UCSR0C |= _BV(UPM01); break;
                case 2: UCSR0C |= (_BV(UPM01)|_BV(UPM00)); break;
                default: break;
        }

        if(NUM_STOP==2) UCSR0C |= _BV(USBS0);
        if(CLK_POL) UCSR0C |= _BV(UCPOL0);
}


/**
 * USART_EnableTx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se habilita para transmision 
 * @RETURN: Void
 */
void USART_EnableTx(){
        UCSR0B |= _BV(TXEN0);
}

/**
 * USART_DisableTx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se deshabilita para transmision 
 * @RETURN: Void
 */
void USART_DisableTx(){
        UCSR0B &= ~_BV(TXEN0);
}

/**
 * USART0_EnableRx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se habilita para Recepsion 
 * @RETURN: Void
 */
void USART_EnableRx(){
        UCSR0B |= _BV(RXEN0);
}


/**
 * USART_DisableRx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se deshabilita para Recepcion 
 * @RETURN: Void
 */
 void USART_DisableRx(){
        UCSR0B &= ~_BV(RXEN0);
 }


 /**
  * USART0_Enviar
  * @PARAMS uint8_t dato -> Dato que se desea enviar
  * @PRE:   El puerto USART0 se encuentra configurado y habilitado para el envio de mensajes+
  * @POST:  Se coloca el mensaje en el buffer de salida de la USART0
  * @RETURN: Void
  */
void USART_Enviar(uint8_t dato){

        // Espera a que se se termina la transmision anterior si esta en curso
        while ( !( UCSR0A & (1<<UDRE0)) );
        // Coloca el dato a enviar en el buffer de salida
        UDR0 = dato;

}


/**
 * USART0_Recibir
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra configurado y habilitado para la recepcion de mensajes
 * @POST:   Se retorno el mensaje que se almaceno en el buffer de entrada de la USART0
 * @RETURN: uint8_t dato -> El dato recibido en la transmision
 */
uint8_t USART_Recibir(){

        // Se espera a que se reciba un mensaje si esta no ha llegado todavia
        while ( !(UCSR0A & (1<<RXC0)) );
        return UDR0;
}


/**
 * USART_EnviarStrLen(int len, char *buf)
 * @PARAMS: int len -> Longitud de la cadena a enviar; char *buf -> Apuntador a la cadena que se desa enviar
 * @PRE:
 * @POST:
 * @RETURN:
 */
void USART_EnviarStrLen(int len, char *buf){

        for (; len > 0; len--){
                USART_Enviar(*buf++);
        }
}
