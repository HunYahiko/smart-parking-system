
#define FOSC 16000000 // Clock Speed = 16MHz
#define BAUD 9600
#define MYUBRR FOSC/16/BAUD-1//This is the value that the counter needs in its register in order to achieve desired baud rate
#define USART_BAUDRATE 9600
#define BAUD_PRESCALE (((FOSC / (USART_BAUDRATE * 16UL)))-1)

volatile char dataIn[20];
unsigned char dataOut[20];
volatile unsigned int dataCount = 0;
volatile uint8_t messageReceived;
const uint8_t DERE_pin = 5;
const uint8_t TRIGGER_PIN = 3;
const uint8_t ECHO_PIN = 4;
const uint8_t BLOCKED_LED_PIN = 9;
const uint8_t FREE_LED_PIN = 6;
const uint8_t OCCUPIED_LED_PIN = 7;
const uint8_t DEBUG_PIN = 8;
const size_t PS_ADDRESS = 2;

/*
   0 - FREE
   1 - OCCUPIED
   2 - BOOKED/BLOCKED
*/
uint8_t PS_STATUS = 0;

void USART_Init(uint16_t UBRR);
void send_char(char c);
void send_string(char string[]);
char** parseMessage(char* message);
char** str_split(char* a_str, const char a_delim);
int isOccupied();
int getStatus();
size_t split_string(char *string, char **splitMessage);

void setup() {
  pinMode(DERE_pin, OUTPUT);
  pinMode(TRIGGER_PIN, OUTPUT);
  pinMode(ECHO_PIN, INPUT);
  pinMode(BLOCKED_LED_PIN, OUTPUT);
  pinMode(FREE_LED_PIN, OUTPUT);
  pinMode(DEBUG_PIN, OUTPUT);
  pinMode(13, OUTPUT);
  digitalWrite(DERE_pin, 0);
  digitalWrite(13, 0);
  digitalWrite(FREE_LED_PIN, 0);
  digitalWrite(BLOCKED_LED_PIN, 0);

  dataCount = 0;
  messageReceived = 0;
  USART_Init(BAUD_PRESCALE);
  sei();
}

void loop() {
  if (messageReceived) {
    cli();
    messageReceived = 0;
    dataCount = 0;
    digitalWrite(13, 0);
    //char** message = str_split(dataIn, ',');
    char** message = malloc(sizeof(char*) * 10);
    split_string(dataIn, message);
    const char* address = *(message + 0);
    const char* functionMessage = message[1];
    //const char* functionMessage = *(message + 1);

    int receivedAddress = atoi(address);
    if (receivedAddress == 2) {
      digitalWrite(DERE_pin, 1);
      char reply[20];
      strcpy(reply, address);
      strcat(reply, ",");

      int function = 0;
      if (functionMessage) {
        function = atoi(functionMessage);
      }
      switch (function) {
        case 1: {
            int parkingLotStatus = getStatus();
            if (parkingLotStatus == 0) {
              digitalWrite(BLOCKED_LED_PIN, 0);
              digitalWrite(OCCUPIED_LED_PIN, 0);
              digitalWrite(FREE_LED_PIN, 1);
              strcat(reply, "0");
            }
            if (parkingLotStatus == 1) {
              digitalWrite(BLOCKED_LED_PIN, 0);
              digitalWrite(OCCUPIED_LED_PIN, 1);
              digitalWrite(FREE_LED_PIN, 0);
              strcat(reply, "1");
            }
            if (parkingLotStatus == 2) {
              digitalWrite(BLOCKED_LED_PIN, 1);
              digitalWrite(OCCUPIED_LED_PIN, 0);
              digitalWrite(FREE_LED_PIN, 0);
              strcat(reply, "2");
            }
            break;
          }
        case 2: {
            if (PS_STATUS == 0) {
              digitalWrite(BLOCKED_LED_PIN, 1);
              digitalWrite(OCCUPIED_LED_PIN, 0);
              digitalWrite(FREE_LED_PIN, 0);
              strcat(reply, "OK");
              PS_STATUS = 2;
            } else {
              strcat(reply, "FAIL");
            }
            break;
            // block barrier
          }
        case 3: {
            // unblock barrier
            digitalWrite(OCCUPIED_LED_PIN, 0);
            digitalWrite(BLOCKED_LED_PIN, 0);
            digitalWrite(FREE_LED_PIN, 1);
            strcat(reply, "OK");
            PS_STATUS = 0;
            break;
          }
        default: {
            strcat(reply, "NaN");
            digitalWrite(13, 1);
            break;
          }
      }
      send_string(reply);

      delay(10);
      digitalWrite(DERE_pin, 0);
    }
    //    for (int i = 0; * (message + i); i++)
    //    {
    //      send_string( *(message + i));
    //      free(*(message + i));
    //    }
    memset(dataIn, 0, sizeof(dataIn));
    sei();
    free(message);
    digitalWrite(DEBUG_PIN, 0);
  }
}

void send_char(char c)
{
  while (!(UCSR0A & (1 << UDRE0))) {};
  UDR0 = c;
}


void send_string(char string[])
{
  int i = 0;

  while (string[i] != 0x00)
  {
    send_char(string[i]);
    i++;
  }
  send_char('\n');
}

void USART_Init(uint16_t UBRR) {
  // This is initializing UART0
  /*Set baud rate (Basically just setting the constant Value determined by MYUBRR in the UBRR0H&L Register */
  UBRR0H = (unsigned char)(UBRR >> 8);
  UBRR0L = (unsigned char)UBRR;
  /* Enable receiver, transmitter and transmit buffer interrupt */
  UCSR0B |= (1 << RXEN0) | (1 << TXEN0) | (1 << RXCIE0);
  /* Set frame format: 8data, 1stop bit */
  UCSR0C |=  (1 << UCSZ01) | (1 << UCSZ00);
}

ISR(USART_RX_vect) {
  char d = UDR0;
  digitalWrite(13, 1);
  dataIn[dataCount] = d;
  ++dataCount;
  if (dataIn[dataCount - 1] == '\n') {
    messageReceived = 1;
  }
}

char** parseMessage(char *message) {
  char *p;
  const char* delim = ",";
  int index = 0;
  char *token[3];
  p = strtok(message, delim);
  while (p != NULL) {
    token[index++] = p;
    //send_string(tokens[index]);
    p = strtok(NULL, delim);
  }
  return token;

}

char** str_split(char* a_str, const char a_delim)
{
  char** result    = 0;
  size_t count     = 0;
  char* tmp        = a_str;
  char* last_comma = 0;
  char delim[2];
  delim[0] = a_delim;
  delim[1] = 0;

  /* Count how many elements will be extracted. */
  while (*tmp)
  {
    if (a_delim == *tmp)
    {
      count++;
      last_comma = tmp;
    }
    tmp++;
  }

  /* Add space for trailing token. */
  count += last_comma < (a_str + strlen(a_str) - 1);

  /* Add space for terminating null string so caller
     knows where the list of returned strings ends. */
  count++;

  result = malloc(sizeof(char*) * count);

  if (result)
  {
    size_t idx  = 0;
    char* token = strtok(a_str, delim);

    while (token)
    {
      *(result + idx++) = strdup(token);
      token = strtok(0, delim);
    }
    *(result + idx) = 0;
  }

  return result;
}

int isOccupied() {
  int occupied;
  digitalWrite(TRIGGER_PIN, LOW);
  delayMicroseconds(2);
  digitalWrite(TRIGGER_PIN, HIGH);
  delayMicroseconds(10);
  digitalWrite(TRIGGER_PIN, LOW);
  long duration = pulseIn(ECHO_PIN, HIGH);
  int cm = duration / 29 / 2;
  if (cm > 10) {
    occupied = 0;
  } else {
    occupied = 1;
  }
  return occupied;
}

int getStatus() {
  if (PS_STATUS == 2) return PS_STATUS;

  PS_STATUS = isOccupied();
  return PS_STATUS;
}

size_t split_string(char *string, char **splitMessage) {
  size_t i = 0;

  char *token = strtok(string, ",");
  while (token != NULL)
  {
    splitMessage[i++] = token;
    token = strtok(NULL, ",");
  }
  return i;
}
