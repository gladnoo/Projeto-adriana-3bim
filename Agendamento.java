public abstract class Agendamento {
    private String nomePet;
    private String especie;
    private String nomeDono;
    private String telefoneDono;
    private int indiceHorario; // 0..9

    public Agendamento(String nomePet, String especie, String nomeDono, String telefoneDono, int indiceHorario) {
        this.nomePet = nomePet;
        this.especie = especie;
        this.nomeDono = nomeDono;
        this.telefoneDono = telefoneDono;
        this.indiceHorario = indiceHorario;
    }

    public String getNomePet() { return nomePet; }
    public void setNomePet(String nomePet) { this.nomePet = nomePet; }
    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }
    public String getNomeDono() { return nomeDono; }
    public void setNomeDono(String nomeDono) { this.nomeDono = nomeDono; }
    public String getTelefoneDono() { return telefoneDono; }
    public void setTelefoneDono(String telefoneDono) { this.telefoneDono = telefoneDono; }
    public int getIndiceHorario() { return indiceHorario; }
    public void setIndiceHorario(int indiceHorario) { this.indiceHorario = indiceHorario; }

    public abstract String getServico();

    @Override
    public String toString() {
        return String.format("%s | Pet: %s (%s) | Dono: %s | Tel: %s",
                getServico(), nomePet, especie, nomeDono, telefoneDono);
    }
}
