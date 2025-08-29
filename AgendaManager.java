import java.util.Scanner;

public class AgendaManager {
    public static final String[] HORARIOS = {
        "09:00","10:00","11:00","12:00","13:00",
        "14:00","15:00","16:00","17:00","18:00"
    };
    private static final Agendamento[] agenda = new Agendamento[HORARIOS.length];

    // ===== Helpers =====
    private static boolean cheia() {
        for (Agendamento a : agenda) if (a == null) return false;
        return true;
    }

    //  Formatar telefone na listagem: 9 dígitos -> 12345-6789 | 8 dígitos -> 1234-5678
    private static String formatTelefone(String tel) {
        if (tel == null) return "";
        String t = tel.replaceAll("\\D", "");
        if (t.matches("\\d{9}")) {
            return t.substring(0,5) + "-" + t.substring(5);
        } else if (t.matches("\\d{8}")) {
            return t.substring(0,4) + "-" + t.substring(4);
        }
        return tel; // fallback
    }

    // 3) Bloquear vazio
    private static String lerNaoVazio(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Campo obrigatório. Não pode ficar vazio.");
        }
    }

    // Reaproveitar leitura de int
    private static int lerInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try { return Integer.parseInt(s); }
            catch (NumberFormatException e) { System.out.println("Número inválido, tenta de novo."); }
        }
    }

    private static int lerIndiceHorario(Scanner sc, String prompt) {
        int op;
        while (true) {
            op = lerInt(sc, prompt);
            if (op >= 1 && op <= HORARIOS.length) break;
            System.out.println("Índice inválido. Vai de 1 a 10.");
        }
        return op - 1;
    }

    // 2) Confirmação genérica
    private static boolean confirmar(Scanner sc, String pergunta) {
        while (true) {
            System.out.print(pergunta + " [s/n]: ");
            String r = sc.nextLine().trim().toLowerCase();
            if (r.equals("s") || r.equals("sim")) return true;
            if (r.equals("n") || r.equals("nao") || r.equals("não")) return false;
            System.out.println("Responde com 's' ou 'n'.");
        }
    }

    // ===== Operações =====
    public static void listar() {
        System.out.println("\n==== CONSULTA DE AGENDA ====");
        for (int i = 0; i < HORARIOS.length; i++) {
            String status = (agenda[i] == null) ? "DISPONÍVEL" : "OCUPADO";
            System.out.printf("[%02d] %s — %s", i + 1, HORARIOS[i], status);
            if (agenda[i] != null) {
                Agendamento a = agenda[i];
                String telFmt = formatTelefone(a.getTelefoneDono());
                System.out.printf(" — %s | Pet: %s (%s) | Dono: %s | Tel: %s",
                        a.getServico(), a.getNomePet(), a.getEspecie(), a.getNomeDono(), telFmt);
            }
            System.out.println();
        }
        System.out.println("============================\n");
    }

    public static void agendar(Scanner sc) {
        if (cheia()) {
            System.out.println("Agenda cheia hoje, Sem vagas.");
            return;
        }
        listar();
        int idx = lerIndiceHorario(sc, "Escolha um horário DISPONÍVEL (1-10): ");
        if (agenda[idx] != null) {
            System.out.println("Horario ocupado.");
            return;
        }

        // 3) Campos não podem ser vazios
        String nomePet   = lerNaoVazio(sc, "Nome do Pet: ");
        String especie   = lerNaoVazio(sc, "Espécie (cachorro, gato...): ");
        String nomeDono  = lerNaoVazio(sc, "Nome do Dono: ");

        // Telefone: só números + 8 ou 9 dígitos
        String tel;
        while (true) {
            System.out.print("Telefone do Dono (8 ou 9 dígitos, apenas números): ");
            tel = sc.nextLine().trim();
            if (tel.matches("\\d{8,9}")) break;
            System.out.println("Telefone inválido. Digite apenas números com 8 ou 9 dígitos.");
        }

        System.out.println("Serviço: [1] Banho Simples  |  [2] Banho + Tosa");
        int tipo = lerInt(sc, "Escolha 1 ou 2: ");

        Agendamento novo;
        if (tipo == 2) {
            novo = new BanhoComTosa(nomePet, especie, nomeDono, tel, idx);
        } else {
            novo = new BanhoSimples(nomePet, especie, nomeDono, tel, idx);
        }
        agenda[idx] = novo;
        System.out.println("Agendado com sucesso em " + HORARIOS[idx] );
    }

    public static void editar(Scanner sc) {
        listar();
        int idx = lerIndiceHorario(sc, "Qual horário deseja editar? (1-10): ");
        if (agenda[idx] == null) {
            System.out.println("Não tem nada aí pra editar");
            return;
        }
        Agendamento a = agenda[idx];
        System.out.println("Editando: " + a);

        // 4) Menu robusto: trata opção inválida e segue o fluxo
        System.out.println("O que quer editar?");
        System.out.println("1-Nome do Pet | 2-Espécie | 3-Nome do Dono | 4-Telefone");
        System.out.println("5-Serviço     | 6-Mudar horário");
        int op = lerInt(sc, "Opção: ");

        switch (op) {
            case 1:
                a.setNomePet(lerNaoVazio(sc, "Novo nome do pet: "));
                break;
            case 2:
                a.setEspecie(lerNaoVazio(sc, "Nova espécie: "));
                break;
            case 3:
                a.setNomeDono(lerNaoVazio(sc, "Novo nome do dono: "));
                break;
            case 4:
                String novoTel;
                while (true) {
                    System.out.print("Novo telefone (11 ou 12 dígitos, apenas números): ");
                    novoTel = sc.nextLine().trim();
                    if (novoTel.matches("\\d{11,12}")) break;
                    System.out.println("Telefone inválido. Digite apenas números com 8 ou 9 dígitos.");
                }
                a.setTelefoneDono(novoTel);
                break;
            case 5:
                System.out.println("Serviço: [1] Banho Simples  |  [2] Banho + Tosa");
                int tipo = lerInt(sc, "Escolha 1 ou 2: ");
                Agendamento novo = (tipo == 2)
                        ? new BanhoComTosa(a.getNomePet(), a.getEspecie(), a.getNomeDono(), a.getTelefoneDono(), a.getIndiceHorario())
                        : new BanhoSimples(a.getNomePet(), a.getEspecie(), a.getNomeDono(), a.getTelefoneDono(), a.getIndiceHorario());
                agenda[idx] = novo;
                break;
            case 6:
                listar();
                int novoIdx = lerIndiceHorario(sc, "Mover para qual horário? (1-10): ");
                if (agenda[novoIdx] != null) {
                    System.out.println("Esse horário já tá ocupado, tente outro.");
                    return;
                }
                agenda[novoIdx] = agenda[idx];
                agenda[idx] = null;
                agenda[novoIdx].setIndiceHorario(novoIdx);
                System.out.println("Movido pra " + HORARIOS[novoIdx] );
                break;
            default:
                System.out.println("Opção inválida. Voltando pro menu principal…");
                return;
        }
        System.out.println("Edição feita. ✔");
    }

    public static void excluir(Scanner sc) {
        listar();
        int idx = lerIndiceHorario(sc, "Qual horário deseja excluir? (1-10): ");
        if (agenda[idx] == null) {
            System.out.println("Horário vazio. Nada pra excluir.");
            return;
        }

        // 2) Confirmar exclusão
        Agendamento a = agenda[idx];
        String telFmt = formatTelefone(a.getTelefoneDono());
        System.out.println("Você vai excluir: " + a.getServico() + " | " +
                a.getNomePet() + " (" + a.getEspecie() + ") | Dono: " + a.getNomeDono() +
                " | Tel: " + telFmt + " @ " + HORARIOS[idx]);

        if (!confirmar(sc, "Tem certeza que deseja excluir?")) {
            System.out.println("Exclusão cancelada. ");
            return;
        }

        agenda[idx] = null;
        System.out.println("Agendamento cancelado. Vaga liberada em " + HORARIOS[idx] + " ✅");
    }
}
