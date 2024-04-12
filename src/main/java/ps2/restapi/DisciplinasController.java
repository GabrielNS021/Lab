package ps2.restapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DisciplinasController {
    private List<Disciplinas> disciplinas;

    public DisciplinasController() {
        this.disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplinas(1, "Etica", "ETC", "Sistemas de Informacao", 1));
        disciplinas.add(new Disciplinas(2, "Programacao", "PRG", "Ciencias da Computacao", 2));
        disciplinas.add(new Disciplinas(3, "Matematica", "MAT", "Administracao", 1));
    }

    @GetMapping("/api/disciplinas")
    Iterable<Disciplinas> getDisciplinas() {
        return this.disciplinas;
    }

    @GetMapping("/api/disciplinas/{id}")
    Optional<Disciplinas> getDisciplinas(@PathVariable long id) {
        for (Disciplinas d: disciplinas) {
            if (d.getId() == id) {
                return Optional.of(d);
            }
        }
        return Optional.empty();
    }

    @PostMapping("/api/disciplinas")
	Disciplinas createDisciplinas(@RequestBody Disciplinas d) {
		long maxId = 1;
		for (Disciplinas dis: disciplinas) {
			if (dis.getId() > maxId) {
				maxId = dis.getId();
			}
		}
		d.setId(maxId+1);
		disciplinas.add(d);
		return d;
	}

    @PutMapping("/api/disciplinas/{disciplinasId}")
    Optional<Disciplinas> updateDisciplinas(@RequestBody Disciplinas disciplinasRequest, @PathVariable long disciplinasId) {
        Optional<Disciplinas> opt = this.getDisciplinas(disciplinasId);
        if (opt.isPresent()) {
            Disciplinas disciplinas = opt.get();
            disciplinas.setNome(disciplinasRequest.getNome());
            disciplinas.setSigla(disciplinasRequest.getSigla());
            disciplinas.setCurso(disciplinasRequest.getCurso());
            disciplinas.setSemestre(disciplinasRequest.getSemestre());
        }
    
        return opt;				
    }	

    @DeleteMapping(value = "/api/disciplinas/{id}")
	void deleteDisciplinas(@PathVariable long id) {
		disciplinas.removeIf(d -> d.getId() == id);
	}	
}
