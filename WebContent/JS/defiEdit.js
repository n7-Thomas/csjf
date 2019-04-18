/**
 * 
 */
function modif(){
	i = document.editDefi.liste.selectedIndex;
	if (i == 0) {
		document.getElementById('formModif').style.visibility='hidden';
	}else {
		document.getElementById('formModif').style.visibility='visible';
	}
	id = document.editDefi.liste.options[i].value;
	document.getElementById('formModif_id_defi').value = id;
	console.log(id)
	
}