$(function() {
    $.ajaxSetup({
        beforeSend : function(xhr, settings) {
          if (settings.type == 'POST' || settings.type == 'PUT'
              || settings.type == 'DELETE') {
            if (!(/^http:.*/.test(settings.url) || /^https:.*/
                .test(settings.url))) {
              xhr.setRequestHeader("X-XSRF-TOKEN",
                  Cookies.get('XSRF-TOKEN'));
            }
          }
        }
      });

    $.get("/user", function(data) {
        $("#user").html(data.name);
        $(".unauthenticated").hide();
        $(".authenticated").show();
        getRepositories();
        getHistory();
    });

    $('#logout').click(function() {
        $.post("/logout", function() {
                   $("#user").html('');
                   $('#repositories').html('');
                   $('#history').html('');
                   $(".unauthenticated").show();
                   $(".authenticated").hide();
               })
        return true;
    });
});

function getRepositories() {
    $.get("/repos", function(data) {
            for (var i = 0; i < data.length; i++) {
                appendRepository(data[i]);
            }
        });
}

function appendRepository(repository) {
    $('#repositories').append('<li><a href=' + repository.html_url + '>' + repository.name + '</a></li>')
}

function getHistory() {
     $.get("/history/all", function(data) {
            for (var i = data.length - 1; i >= 0; i--) {
                appendHistoryEntity(data[i]);
            }
        });
}

function appendHistoryEntity(historyEntity) {
    if (historyEntity.login) {
        if (historyEntity.currentUser)
            $('#history').append('<li><strong>' + historyEntity.username + " авторизовался в " + parseTimeStamp(historyEntity.timeStamp) + '</strong></li>');
        else
            $('#history').append('<li>' + historyEntity.username + " авторизовался в " + parseTimeStamp(historyEntity.timeStamp) + '</li>');
    } else {
        if (historyEntity.currentUser)
            $('#history').append('<li><strong>' + historyEntity.username + " вышел в " + parseTimeStamp(historyEntity.timeStamp) + '</strong></li>');
        else
            $('#history').append('<li>' + historyEntity.username + " вышел в " + parseTimeStamp(historyEntity.timeStamp) + '</li>');
    }
}

function parseTimeStamp(timeStamp) {
    // 2018-05-23T14:31:17.000+0000
    timeStampTemp = timeStamp.split('T');
    time = timeStampTemp[1].split('.')[0]
    date = timeStampTemp[0].split('-')
    return time + ' ' + date[2] + '.' + date[1] + '.' + date[0];
}