/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SampleQuestions;

/**
 *
 * @author ASUS
 */
import java.util.ArrayList;
import java.util.List;

public class SampleQuestions {
    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question("What is the OSI model?", 
                new String[]{"Open System Interconnection model", "Operating System Integration model", "Open System Integration model", "Operating System Interconnection model"}, 0));

        questions.add(new Question("Which protocol is used for sending and receiving emails?", 
                new String[]{"HTTP", "SMTP", "FTP", "SSH"}, 1));

        questions.add(new Question("What does DNS stand for?", 
                new String[]{"Dynamic Network Service", "Domain Name System", "Data Network Service", "Digital Name System"}, 1));

        questions.add(new Question("In networking, what is the purpose of NAT (Network Address Translation)?", 
                new String[]{"To convert domain names to IP addresses", "To encrypt network traffic", "To map private IP addresses to a public IP address", "To authenticate network users"}, 2));

        questions.add(new Question("What is a firewall in the context of network security?", 
                new String[]{"A device used to amplify network signals", "A protective barrier that filters network traffic", "A device used for routing network traffic", "A tool for monitoring network bandwidth"}, 1));

        questions.add(new Question("Which protocol is used for secure shell access to a remote server?", 
                new String[]{"HTTP", "FTP", "SSH", "SMTP"}, 2));

        questions.add(new Question("What is the subnet mask for a typical IPv4 private network?", 
                new String[]{"255.0.0.0", "255.255.255.0", "255.255.0.0", "255.0.255.0"}, 2));

        questions.add(new Question("What is the purpose of a MAC address in networking?", 
                new String[]{"To identify a device on a local network", "To determine the public IP address of a device", "To establish a secure VPN connection", "To encrypt network traffic"}, 0));

        questions.add(new Question("What does HTTP stand for in the context of web communication?", 
                new String[]{"Hypertext Transfer Protocol", "High-Tech Transfer Protocol", "Home Transfer Protocol", "Hypertext Terminal Protocol"}, 0));

        questions.add(new Question("What is a VLAN (Virtual Local Area Network)?", 
                new String[]{"A physical network switch", "A logical network segment within a larger physical network", "A type of network cable", "A wireless networking standard"}, 1));

        return questions;
    }
}
