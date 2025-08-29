public class BanhoSimples extends Agendamento {
    public BanhoSimples(String nomePet, String especie, String nomeDono, String telefoneDono, int indiceHorario) {
        super(nomePet, especie, nomeDono, telefoneDono, indiceHorario);
    }
    @Override
    public String getServico() { return "Banho Simples"; }
}
