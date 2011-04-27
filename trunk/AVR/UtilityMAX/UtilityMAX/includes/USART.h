/**
 * -------------------------------------------------------------------
 * Copyright (c) 2010 - All rights reserved
 * Manuel Eduardo Vieda Salomon <mail@manuelvieda.com>
 * Electronic Engineer, Computer Science Engineer
 * University of Los Andes. Bogotá, Colombia.
 * -------------------------------------------------------------------
 * 
 * FILE:		USART.h
 * CREATED:		31/03/2011
 * MODIFIED:	31/03/2011
 * AUTHOR:		Manuel Vieda <http://manuelvieda.com>
 */ 

#define F_CPU 20000000UL

#ifndef USART_H_
#define USART_H_

//------------------------------------------------------------------------
//  PPARAMETERS THAN CAN BE CONFIGURED BY THE USER
//------------------------------------------------------------------------


#ifndef F_CPU
        #error "ERROR: The clock's frecuency is not defined (F_CPU). A default 8Mhz frecuency has been asumed"
        #define F_CPU 20000000UL
#endif


/**
 *  Baud rate
 */
#define BAUDRATE 9600

/**
 * ASY_SYN=1  Asynchronous mode
 * ASY_SYN=0  Synchronous mode
 */
#define ASY_SYN 1

/**
 * INT_TX_COMPLETA=1  A interruption is generated, if globally enabled, when the transmission is completed
 * ITN_TX_COMPLETA=0  No interruptions
 */
#define INT_TX_COMPLETA 0

/**
 * INT_RX_COMPLETA=1  A interruption is generated, if globally enabled, when a reception of a byte is completed
 * INT_RX_COMPLETA=0  No interruptions
 */
#define INT_RX_COMPLETA 0

/**
 * SPEED_2X=1  Se habilita el doble de velocidad de transmision si se esta trabajanod en modo
 *             asincrono. Tener en cuenta que se realizaran menos muestro, por lo que solo debe
 *             activarse con un BAUDRATE adecuado y en medios de bajo ruido.
 * SPEED_2X=0  Velocidad de transmision normal
 */
#define SPEED_2X 0

/**
 * CONTROL_PARIEDAD=0  No se realiza ningun tipo de control de pariedad en la informacion transmitida
 * CONTROL_PARIEDAD=1  Se genera automaticamente un bit de pariedad PAR al enviar un dato y el
 *                     receptor se encarga de comprobarlo. Esta dato se revisa manualmente.
 * CONTROL_PARIEDAD=2  Se genera automaticamente un bit de pariedad IMPAR al enviar un dato y el
 *                     receptor se encarga de comprobarlo. Esta dato se revisa manualmente.
 */
#define CONTROL_PARIEDAD 0

/**
 * Se define el numero de bits de STOP que se envia en cada comunicacion. Puede tomar el valor de 1 o 2
 */
#define NUM_STOP 1

/**
 * Se define el tamañao en bits que conforma cada caracter enviado por la USART en cada una de las 
 * transimisiones. Este valor puede tomar valores de 5,6,7,8 y 9 bits. 
 */
#define CHAR_SIZE 8

/**
 * Se define la polaridad del reloj (Unicamente cuando se trabaja en modo sincrono). 
 * CLK_POL=0 Salida de Tx en flanco de subida, Rx en flanco de bajada
 * CLK_POL=1 Salida de Tx en flanco de bajada, Rx en flanco de subida
 */
#define CLK_POL 0


//-------------------------------------------------------------------------------
//  FUNCIONES OFRECIDAS QUE PUEDEN SER LLAMADAS POR EL USUARIO
//-------------------------------------------------------------------------------

/**
 * Se define el valor del registro que controla la tasa de transimision en baudios
 */
#define UBRRVAL ((F_CPU/(BAUDRATE*16UL))-1)

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
void USART_init();

/**
 * USART_EnableTx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se habilita para transmision 
 * @RETURN: Void
 */
void USART_EnableTx();

/**
 * USART_DisableTx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se deshabilita para transmision 
 * @RETURN: Void
 */
void USART_DisableTx();

/**
 * USART_EnableRx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se habilita para Recepsion 
 * @RETURN: Void
 */
void USART_EnableRx();

/**
 * USART_DisableRx
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra inicializado
 * @POST:   El puerto USART0 se deshabilita para Recepcion 
 * @RETURN: Void
 */
void USART_DisableRx();

 /**
  * USART_Enviar
  * @PARAMS: uint8_t dato -> Dato que se desea enviar
  * @PRE:    El puerto USART0 se encuentra configurado y habilitado para el envio de mensajes
  * @POST:   Se coloca el mensaje en el buffer de salida de la USART0
  * @RETURN: Void
  */
void USART_Enviar(uint8_t dato);

/**
 * USART_Recibir
 * @PARAMS: Ninguno
 * @PRE:    El puerto USART0 se encuentra configurado y habilitado para la recepcion de mensajes
 * @POST:   Se retorno el mensaje que se almaceno en el buffer de entrada de la USART0
 * @RETURN: uint8_t dato -> El dato recibido en la transmision
 */
uint8_t USART_Recibir();

/**
 * USART_EnviarStrLen
 * @PARAMS: int len -> Longitud de la cadena que se desea transmitir.
 *                      char *buf -> Apuntador al primer caracter de la cadena que se desea transmitir
 * @PRE:    El puerto USART0 se encuentra configurado y habilitado para la recepcion de mensajes
 * @POST:   Se coloca en el buffer de salida cada uno de los caracteres que componen el mensaje que se desa tranmitir [*buf, *buf+len]
 * @RETURN: 
 */
void USART_EnviarStrLen(int len, char *buf);

/**
 * USART_EnviarStr(str)
 * Envia un String por la USART
 */
#define USART_EnviarStr(str) USART_EnviarStrLen(sizeof(str)-1, str)

/**
 * USART_EnviarLn(str)
 * Envian una linea de String (String + /n)
 */
#define USART_EnviarLn(str) USART_EnviarStrLen(sizeof(str)-1, str); USART_Enviar(0x0D)

#endif /* USART_H_ */