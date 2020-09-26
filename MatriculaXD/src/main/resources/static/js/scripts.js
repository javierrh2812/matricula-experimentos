


document.addEventListener("DOMContentLoaded", function(){
	let domain = "http://localhost:8080/"

	
	function load(url, element, fn)
	{
		fetch(url)
		.then(res => res.text())
		.then(data => { element.innerHTML=data	})
		.then( () => {fn()})
	
	}
	
	
	
	/**METODO PARA LISTAR ALUMNOS Y CURSOS DEL SEMESTRE**/
	
	
	if(document.title=='Alumnos Matriculados'){
		
		const semestreSelect = document.querySelector("#semestreSelect")
		const replaceDiv = document.querySelector("div#replace")
		
		replaceDiv.innerHTML="Elige un Semestre para ver el reporte."
		
		semestreSelect.onchange= ()=>{
		 	console.log(document.URL+select.value)
		}
	}
	
	
	//MÉTODO PARA LAS LINEAS DE CURSOS MATRICULADOS CON DOCENTE
	
	if (document.title=='Nueva Matricula'||document.title=='Editar Matricula'){
		
		const docentesDiv = document.querySelector("#docentesDiv")
		const cursoSelect = document.querySelector("#cursoSelect")
		const cursosTBody = document.querySelector("#cursosMatriculados")
		const agregarBtn = document.getElementById("btnAgregar")
		let docenteSelect = document.querySelector("#docenteSelect")
		
		agregarBtn.addEventListener("click", e=> e.preventDefault())
		
		cursoSelect.onchange = () => {
			let url = domain+'docentes/api?curso='+cursoSelect.value
			load(url, docentesDiv, 
			()=>{
			docenteSelect = document.querySelector("#docenteSelect")})
			
		}
			
		agregarBtn.onclick= e=>{
		
		if (cursoSelect.value != '0' && docenteSelect.value != '0'){ 
		
			if (!hasCurso()){
			
				//SE AÑADE UNA LINEA DE CURSO, SI ES QUE NO ESTÁ AGREGADO EL CURSO
				
				cursosTBody.innerHTML+=
				
				`<tr id="linea${cursoSelect.selectedIndex}">
					<td id="curso${cursoSelect.selectedIndex}"><input name="cursos[]" value="${cursoSelect.value}"/></td>
					<td class="d-none"><input type="hidden" value="${docenteSelect.value}"name="docentes[]" /></td>
					<td>${docenteSelect.options[docenteSelect.selectedIndex].text}</td>
					<td><a href="#" onclick="parentNode.parentNode.parentNode.removeChild(parentNode.parentNode)">Quitar</a></td>
				</tr>`
			
			}
			
			else{

				//SI EL CURSO YA ESTÁ AGREGADO, SOLO SE REEMPLAZA LA LINEA SI CAMBIA DE PROFESOR
					
				let el = "linea" + cursoSelect.selectedIndex

				document.getElementById(el).innerHTML=
				`<td id="curso${cursoSelect.selectedIndex}"><input name="cursos[]" value="${cursoSelect.value}"/></td>
				<td class="d-none"><input type="hidden" value="${docenteSelect.value}"name="docentes[]" /></td>
				<td>${docenteSelect.options[docenteSelect.selectedIndex].text}</td>
				<td><a href="#" onclick="parentNode.parentNode.parentNode.removeChild(parentNode.parentNode)">Quitar</a></td>`
			}
		}
		
		}
		function hasCurso(){
			for (let el of Array.from(document.querySelectorAll("td[id*='curso']"))){
				if (el.id == 'curso'+cursoSelect.selectedIndex) return true
			}
			return false
		}
		
		
	}

	


})




