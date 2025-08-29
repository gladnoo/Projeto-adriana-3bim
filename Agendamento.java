public abstract class Agendamento {

    private String nomePet;
    private String especie;
    private String nomeDono;
    private String telefoneDono;


    private int indiceHorario; // 0..9

    public Agendamento(String nomePet, String especie, String nomeDono, String telefoneDono, int indiceHorario) {
        // Pequeno cuidado: evita espaços perdidos sem mudar a regra de negócio
        this.nomePet = safeTrim(nomePet);
        this.especie = safeTrim(especie);
        this.nomeDono = safeTrim(nomeDono);
        this.telefoneDono = safeTrim(telefoneDono);
        this.indiceHorario = indiceHorario;
    }

    public abstract String getServico();

    public String getNomePet() { return nomePet; }
    public void setNomePet(String nomePet) { this.nomePet = safeTrim(nomePet); }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = safeTrim(especie); }

    public String getNomeDono() { return nomeDono; }
    public void setNomeDono(String nomeDono) { this.nomeDono = safeTrim(nomeDono); }

    public String getTelefoneDono() { return telefoneDono; }
    public void setTelefoneDono(String telefoneDono) { this.telefoneDono = safeTrim(telefoneDono); }

    public int getIndiceHorario() { return indiceHorario; }
    public void setIndiceHorario(int indiceHorario) { this.indiceHorario = indiceHorario; }

    private static String safeTrim(String s) {
        return s == null ? null : s.trim();
    }

    @Override
    public String toString() {
        // ex: "Banho | Pet: Thor (cahcorro) | Dono: joao | Tel: 12929139123"
        return String.format(
                "%s | Pet: %s (%s) | Dono: %s | Tel: %s",
                getServico(),
                nomePet,
                especie,
                nomeDono,
                telefoneDono
        );
    }
}
