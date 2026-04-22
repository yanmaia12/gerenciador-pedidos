package com.yanmaia12.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TratamentoErros {
    static Scanner scanner = new Scanner(System.in);

    public static int tratamentoInteiros(String frase){
        int numero = 0;
        boolean certo = false;

        while (!certo){
            try{
                System.out.print(frase);
                numero = scanner.nextInt();
                scanner.nextLine();

                certo = true;
            }catch (InputMismatchException e){
                System.out.println("erro: O valor inserido deve ser um número inteiro, tente novamente!");
                scanner.nextLine();
            }
        }

        return numero;
    }

    public static Double tratamentoDouble(String frase){
        Double numero = 0.0;
        boolean certo = false;

        while (!certo){
            try{
                System.out.print(frase);
                String numeroString = scanner.nextLine();
                if (numeroString.contains(",")){
                    numeroString = numeroString.replace(",", ".");
                }

                numero = Double.parseDouble(numeroString);

                certo = true;
            }catch (NumberFormatException e){
                System.out.println("erro: O valor inserido deve ser um número com casas decimais, tente novamente!");
                scanner.nextLine();
            }
        }

        return numero;
    }

    public static String tratamentoString(String frase) {
        String entrada = "";
        boolean certo = false;

        while (!certo) {
            System.out.print(frase);
            entrada = scanner.nextLine().trim();

            if (entrada.isBlank()) {
                System.out.println("erro: O campo não pode ficar vazio! Tente novamente.");
            } else {
                certo = true;
            }
        }
        return entrada;
    }

    public static LocalDate tratamentoData(String frase) {
        while (true) {
            try {
                System.out.print(frase + " (aaaa/dd/mm): ");
                String entrada = scanner.nextLine();
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                System.out.println("Erro: Formato de data inválido! Use o padrão dia/mês/ano (ex: 15/04/2026).");
            }
        }
    }
}
