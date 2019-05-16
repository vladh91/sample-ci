import pika
import json
import smtplib
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText

def send_email():
    fromaddr = "your_email"
    toaddr = "recepient_email"
    msg = MIMEMultipart()
    msg['From'] = fromaddr
    msg['To'] = toaddr
    msg['Subject'] = "Jenkins Job Status"
    body = "Jenkins job completed!!!"
    msg.attach(MIMEText(body,'plain'))
    server = smtplib.SMTP('smtp.gmail.com','587')
    server.ehlo()
    server.starttls()
    server.ehlo()
    server.login("your_email_username","your_email_password")
    text = msg.as_string()
    server.sendmail(fromaddr,toaddr,text)

credentials = pika.PlainCredentials('user','password')
connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq',5672,'/',credentials,connection_attempts=100))
channel = connection.channel()

channel.queue_declare(queue='jenkins',durable='false')

def callback(ch, method, properties, body):
    file = json.loads(body)
    if (file["state"]=="COMPLETED"):
        send_email()




channel.basic_consume(callback,
                          queue='jenkins',
                          no_ack=True)
channel.start_consuming()
