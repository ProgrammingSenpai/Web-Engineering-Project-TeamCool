
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Guteantworten</title>
<meta name="_csrf" content="${_csrf.token}" />
<!-- default header name is X-CSRF-TOKEN -->
<meta name="_csrf_header" content="${_csrf.headerName}" />
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://unpkg.com/vue"></script>
                <link rel="stylesheet" href="css.css">
</head>
<body>
    
    <div id="app">
    
            <div id="titleBox" style="width:73em">
            <h1 id="title">Guteantworten.net</h1>
            </div>
            
            <div id="subtitleBox">
            <h1 id="subtitle" v-text="subtitle"></h1>
            <form @submit.prevent="getUnanswered" v-if="showQuestions || subtitle == 'Alle ungelösten Fragen'">
                <input class="AlleFragen" id="getUnansweredQuestion" type="submit" value="Alle unbeantworteten Fragen"/>
                </form>
                <form @submit.prevent="getUnsolved" v-if="showQuestions || subtitle == 'Alle unbeantworteten Fragen'">
                <input class="AlleFragen" id="getUnsolvedQuestion" type="submit" value="Alle ungelösten Fragen"/>
                </form>
            </div>
            
            <div id="Questions" v-if="writeQuestion">
                <input id="frageErstellenTitel" type="text"  v-model = "title" placeholder="Titel" />
                <textarea id="frageErstellenFrage" v-model = "qText" placeholder="Frage"></textarea>
                <input @click.prevent="addQuestion" id="frageErstellenSubmit" type="submit"  value="Frage abschicken" />
            </div>
            
            <div id="Question" v-if="showOneQuestion" style="position:absolute;top:9.5em;">
            	<div style="height:10em;width:15em;border: 0.1em solid activecaption;" >
            	<p style="position:absolute;top:0em;left:1em;text-decoration:underline;">Autor: {{question.author}}</p>
            	<p style="position:absolute;top:2em;left:1em;">Gelöst: {{question.solved}}</p>
            	<p style="position:absolute;top:4em;left:1em;">Datum: {{question.date}}</p>
            	<input v-if="currentUser.name == question.author" @click.prevent="deleteQuestion(question.id)" type="submit" value="Frage löschen" style="position:absolute;top:8em;left:1em;"/>
            	<input v-if="currentUser.name == question.author" @click.prevent="toggleSolved()" type="submit" value="Als beantwortet markieren" style="position:absolute;top:10em;left:1em" v-if="currentUser==question.author"/>
            	</div>
            	<div style="position:absolute;top:0em;height:10em;width:49.5em;left:15em;border: 0.1em solid activecaption;">
            	<p style="position:relative;top:-0.4em;left:0.7em;text-decoration:underline;font-size:1.5em;">{{question.title}}</p>
            	<div style="position:relative;top:-1em; left:1em;width:40em;">{{question.text}}</div>
            	</div>
            	
            	<template v-for="answer in answers">
            	<div style="height:8em;width:61.5em;border: 0.1em solid activecaption;background-color:lightcyan;">
            	<p style="position:relative;width:30em;top:-1em;left:0.5em;text-decoration:underline;font-size:2em;">{{answer.author}}</p>
            	<p style="position:relative;width:30em;top:-4em;left:0.5em;">Hilfreich: {{answer.accepted}}</p>
            	<input type="submit" v-if="currentUser.name == question.author" value="Antwort ist hilfreich" style="position:relative;top:-3em;" @click.prevent="toggleAccepted(answer.id)" v-if="currentUser==question.author">
            	<input type="submit" v-if="currentUser.name == answer.author" value="Antwort löschen" style="position:relative;top:-5em;left:-10em;" @click.prevent="deleteAnswer(answer.id)">
            	<div style="position:relative;top:-8em;left:8.5em;width:40em;">{{answer.text}}</div>
            	</div>
            	</template>
            	
            	<div v-if="loggedIn">
            	<textarea style="width:52em;height:15em;" v-model = "aText" placeholder="Deine Antwort"></textarea>
            	<input @click.prevent="addAnswer" style="position:relative;"type="submit" value="Antwort abschicken"/>
            	</div>
            	
            </div>
   
            <div style="position:absolute;top:9.5em;" v-if="showQuestions" id="allQuestion">
            <template v-for="question in questions">
            <div style="height:19em;width:61.5em;border: 0.1em solid activecaption;background-color:lightcyan;">
            <p style="position:relative;top:-0.5em;left:0.5em;text-decoration:underline;font-size:2em;">{{question.title}}</p>
            <p style="position:relative;top:-2em;left:1em;text-decoration:underline;">Autor:</p>
            <p style="position:relative;width:3em;top:-2.5em;left:1em;">{{question.author}}</p>
            <div style="position:relative;width:12em;top:-6em;left:13em;width:40em;">{{question.text}}</div>
            <p style="position:relative;top:-5em;left:1em;">{{question.date}}</p>
            <p style="position:relative;top:-5em;left:1em;">Gelöst:{{question.solved}}</p>
            <p style="position:relative;top:-5em;left:1em;">Antworten:{{question.answerCount}}</p>
            <input @click.prevent="showQuestion(question.id)" style="position:relative;top:-6.5em;left:1em;" type="submit" value="Zur Frage">
            </div>
            </template>
            </div>
            
            <div id="navigationBox" style="width:11.5em">
            
                <form id="loginForm" name='f' action='/login' method='POST' v-if="!loggedIn">
				<table class="login">
					<tr>
						<td><input type='text' name='username' placeholder="username" value='' ></td>
					</tr>
					<tr>
						<td><input type='password' placeholder="password" name='password'/></td>
					</tr>
					<tr>
						<td colspan='2'><input name="submit" id="login" type="submit"
							value="Login" /></td>
					</tr>
					<input name="_csrf" type="hidden" value="${_csrf.token}" />
				</table>
                </form>
                
                <form v-if="!loggedIn" id="registerForm" @submit.prevent="register">
                <input type='text' value='' placeholder="username" v-model='user'>
                <input type='password' placeholder="password" v-model='pass'/>
                     <input id="register" type='submit' value="Registrate"/>
                </form>
                
                <input @click.prevent="getAll" class="login" id="allQuestionsButton" type="submit" value="Alle Fragen"/>
                
                <input @click.prevent="logout" class="login" id="login" type="submit" value="Logout" v-if="loggedIn"/>
                
                <input @click.prevent="createQuestion" class="login" id="frageErstellenButton" type="submit" v-if="loggedIn" value="Frage erstellen"/>
                
                <input @click.prevent="getMyQuestions" class="login" id="getMyQuestion" type="submit" v-if="loggedIn" value="Meine Fragen"/>
                
                <input @click.prevent="getMyAnswers" class="login" id="getMyAnswer" type="submit" v-if="loggedIn" value="Meine Antworten"/>
                
                <input @click.prevent="initialiceDatenbank" class = "login" id="initialiceDB" type="submit" value="Initialisiere Datenbank"/>
                
            </div>
        </div>
    
    
    
    
    
    
    
    
	<!--<div id="app">
		<h1 id="testHeader"></h1>
		<form @submit.prevent="addQuestion" style="float: left">
			Question erstellen:<br> <input type="text" v-model="qAuthor"></input><br>
			<input type="text" v-model="title"></input> <br>
			<textarea rows="4" cols="50" v-model="qText"></textarea>
			<input type="submit" />
		</form>
		<br>

		<form @submit.prevent="addAnswer" style="float: left">
			Answer erstellen:<br> <input type="number" v-model="qId"></input><br>
			<input type="text" v-model="aAuthor"></input><br>
			<textarea rows="4" cols="50" v-model="aText"></textarea>
			<input type="submit" />
		</form>
		<br>

		<form @submit.prevent="register" style="float: left">
			Registration:<br> <input type="text" v-model="user"></input><br>
			<input type="text" v-model="password"></input> <input type="submit" />
		</form>
	</div>-->

	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="https://unpkg.com/vue"></script>


	<script>
		$(function() {
			var app = new Vue({
				el : "#app",
				data : {
					showQuestions: false,
					writeQuestion: false,
					showOneQuestion: false,
					questions : [],
					loggedIn : false,
					qText : '',
					title : '',
					aText : '',
					qId : '',
					user : '',
					pass : '',
					answers : [],
					question : '',
					currentUser : '',
					subtitle: "Alle Fragen"
				},

				created : function() {
					$.ajax({
						url : 'http://localhost:8080/questions',
						type : 'GET',
						xhrFields : {
							withCredentials : true
						},
						complete : function(xhr, error) {
							if (xhr.status !== 401 && xhr.status !== 403) {
								app.loggedIn = true;
							}
						}
					}),
					$.getJSON('http://localhost:8080/user', function(res) {
						this.currentUser = res;
					}),
					
						$.getJSON('http://localhost:8080/questions/all', function(res) {
							app.questions = res;
						});
					
              this.showQuestions = true;                              
				},

				methods : {
					
					login: function(){
						$.ajax({
							url : 'http://localhost:8080/login',
							type : 'POST',
							xhrFields : {
								withCredentials : true
							},
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content"),
							},
							contentType : 'text/plain',
							data : JSON.stringify({
								username : this.user,
								password : this.pass
							}),
							success : function() {
								this.loggedIn = true;
							}
						});
					},
					
					toggleSolved : function(username,authorname) {
						alert(username),
						alert(authorname),
						$.ajax({
							url : 'http://localhost:8080/question/' + app.question.id,
							type : 'PATCH',
							xhrFields : {
								withCredentials : true
							},
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content"),
							},
							success : function() {
								app.showQuestion(app.question.id);
							}
						});
					},
					initialiceDatenbank : function(){
						$.getJSON('http://localhost:8080/init');
						location.reload();
					},
					toggleAccepted : function(id) {
						$.ajax({
							url : 'http://localhost:8080/answer/' + id,
							type : 'PATCH',
							xhrFields : {
								withCredentials : true
							},
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content"),
							},
							success : function() {
								app.showQuestion(app.question.id);
							}
						});
					},
					showQuestion : function(qId) {
						app.aText = "",
						this.subtitle = "Fragenansicht",
						this.showQuestions = false,
						this.showOneQuestion = true,
						$.getJSON('http://localhost:8080/question/' + qId, function(res) {
							app.question = res;
						});
						$.getJSON('http://localhost:8080/answers/question/' + qId,
								function(res) {
									app.answers = res;
								});
						$.getJSON('http://localhost:8080/user', function(res) {
							app.currentUser = res;
						});
					},
					createQuestion:function(){
						app.qText = "",
						app.title = "",
						this.showQuestions = false,
						this.showOneQuestion= false;
						this.writeQuestion = true,
						this.subtitle = "Frage erstellen"
						//location.reload();
					},
					addQuestion : function() {
						if(this.title.length >= 3 && this.qText.length >= 3){
							this.writeAnswer = false,
							this.showQuestions = true,
						$.ajax({
							url : 'http://localhost:8080/question',
							type : 'POST',
							xhrFields : {
								withCredentials : true
							},
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content"),
							},
							contentType : 'text/plain',
							data : JSON.stringify({
								title : this.title,
								text : this.qText
							}),
							success : function() {
								//app.currentPage = 0;
								app.qText = 'Question Text';
								app.title = 'Title';
								location.reload();
							},
							error: function(){
								alert("Error!");
							},
						}).then(setTimeout(this.getAll(),10000));
						}else{
							alert("Titel und Text müssen mindestens drei Zeichen enthalten!");
						};
					},
					deleteQuestion: function(qId){
						$.ajax({
							url:'http://localhost:8080/question/' + qId,
							type: 'DELETE',
							xhrFields : {
								withCredentials : true
							},
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content")
							},
							success : function() {
								location.reload();
							}
						}).then(setTimeout(this.getAll(),10000));
					},
					addAnswer : function() {
						$.ajax({
							url : 'http://localhost:8080/answer',
							type : 'POST',
							xhrFields : {
								withCredentials : true
							},
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content")
							},
							contentType : 'text/plain',
							data : JSON.stringify({
								text : this.aText,
								qId : this.question.id.toString() //muss zu string konvertiert werden, sonst gibt die HashMap im javacode ein conversion error
							}),
							success : function() {
								app.aText = 'Danke für deine Antwort';
								location.reload();
							},
						}).then(setTimeout(this.showQuestion(this.question.id),10000));
						},
						
						deleteAnswer: function(answerId){
							$.ajax({
								url:'http://localhost:8080/answer/' + answerId,
								type: 'DELETE',
								xhrFields : {
									withCredentials : true
								},
								headers : {
									"X-CSRF-Token" : $("meta[name='_csrf']").attr("content")
								},
								success : function() {
									location.reload();
								}
							}).then(setTimeout(this.showQuestion(this.question.id),10000));
						},
					getAll : function() {
						this.writeQuestion = false,
						this.showOneQuestion = false,
						this.showQuestions = true,
						this.subtitle = "Alle Fragen";
						$.getJSON('http://localhost:8080/questions/all', function(res) {
							app.questions = res;
						})
					},
					getUnanswered : function() {
						this.subtitle = "Alle unbeantworteten Fragen";
						$.getJSON('http://localhost:8080/questions/unanswered', function(
								res) {
							app.questions = res;
						})
					},
					getUnsolved : function() {
						this.subtitle = "Alle ungelösten Fragen";
						$.getJSON('http://localhost:8080/questions/unsolved',
								function(res) {
									app.questions = res;
								})
					},
					getMyQuestions : function() {
						this.questions = [];
						this.subtitle = "Meine Fragen";
						this.showOneQuestion = false;
						this.writeQuestion = false;
						this.showQuestions = true;
						$.getJSON('http://localhost:8080/questions', function(res) {
							app.questions = res;
						})
					},
					getMyAnswers : function() {
						this.questions = [];
						this.writeQuestion = false;
						this.showOneQuestion = false;
						this.showQuestions = true;
						this.subtitle = "Meine Antworten";
						$.getJSON('http://localhost:8080/answers', function(res) {
							app.answers = res;
						})
						$.getJSON('http://localhost:8080/questions/answeredByMe', function(
								res) {
							app.questions = res;
						})
					},
					
					register : function() {
						if(this.user.length >= 3 && this.pass.length >= 3){
						$.ajax({
							url : '/register',
							type : 'POST',
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content")
							},
							contentType : 'text/plain',
							data : JSON.stringify({
								user : this.user,
								password : this.pass,
							}),
							success : function() {
								app.user = 'Erfolgreich Registriert';
								app.pass = 'Registriert';
							}
						});
						}else{
							alert("Username und Passwort müssen mindestens drei Zeichen enthalten!");
						};
					},
					logout : function() {
						$.ajax({
							url : '/logout',
							type : 'POST',
							headers : {
								"X-CSRF-Token" : $("meta[name='_csrf']").attr("content"),
							},
							success : function() {
								this.loggedIn = false;
								location.reload(); //damit der CSRF-Token erneuert wird
								currentUser = '';
							}.bind(this)
						});
					}
				}
			});
		});
	</script>
</body>
</html>