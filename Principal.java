import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== PET SHOP — Agenda de Banho ===");
            System.out.println("1 - Agendar banho");
            System.out.println("2 - Consultar agenda");
            System.out.println("3 - Editar agendamento");
            System.out.println("4 - Excluir agendamento");
            System.out.println("5 - Sair");
            System.out.print("Escolha: ");

            String opt = sc.nextLine().trim();

            if ("1".equals(opt)) {
                AgendaManager.agendar(sc);
            } else if ("2".equals(opt)) {
                AgendaManager.listar();
            } else if ("3".equals(opt)) {
                AgendaManager.editar(sc);
            } else if ("4".equals(opt)) {
                AgendaManager.excluir(sc);
            } else if ("5".equals(opt)) {
                System.out.println("Falou!");
                sc.close();
                return;
            } else {
                System.out.println("Opção inválida, tenta de novo.");
            }
        }
    }
}
