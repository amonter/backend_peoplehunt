function expandAndCollapse(elementHide) {  
    var identity=document.getElementById(elementHide); 
    if(identity.style.display == 'none' || identity.style.display == '') {
      identity.style.display = "block";
      document.getElementById('comment_button').style.display = "none";
    }else{
      identity.style.display = "none";
      document.getElementById('comment_button').style.display = "block";
      document.getElementById('commentId').value = "";
    }
  }
  
  
  function expandAndColapseAll(id,elementHide) {  
    var identity=document.getElementById(elementHide);
    
    if(identity.style.display == 'none' || identity.style.display == '') {
      identity.style.display = "block";
    }else{
      identity.style.display = "none";
    }
    
    var element = document.getElementById(id);
    if(id == 'expand'){
      var colapse = document.getElementById('collapse');
      element.style.display = "none"; 
      colapse.style.display = "block";
    }
    
    if(id == 'collapse'){
      var expand = document.getElementById('expand');
      element.style.display = "none"; 
      expand.style.display = "block";
    }
    
  }