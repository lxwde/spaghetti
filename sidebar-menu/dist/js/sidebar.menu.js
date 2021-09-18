$(() => {
    $('.list-item').each((i, listItem) => {
        // $(el).find('a.link-arrow') also works
        $('a.link-arrow', listItem).each((key, linkArrow) => {
            console.log(key + linkArrow);
            $(linkArrow).addClass('up');
            if ($(linkArrow).hasClass('link-current')) {
                $(linkArrow).addClass('active', 'down');
                $(linkArrow).next().css('display', 'block');
            }

            $(linkArrow).on('click', (event) => {
                event.preventDefault();
                slideToggle($(linkArrow).next(), 200);
                $(linkArrow).addClass('transition');
                $(linkArrow).toggleClass('active');
                $(linkArrow).toggleClass('rotate');
                $(linkArrow).toggleClass('link-current');
            });
        });
    });

    function slideToggle(target, duration = 500) {
        if ($(target).css('display') === 'none') {
            return slideDown(target, duration);
        } else {
            return slideUp(target, duration);
        }
    }

    function slideUp(target, duration = 500) {
        $(target).css('transitionProperty', 'height, margin, padding');
        $(target).css('transitionDuration', duration + 'ms');
        $(target).css('boxSizing', 'border-box');
        $(target).css('height', target.offsetHeight + 'px');
        $(target).css('overflow', 'hidden');
        $(target).css('height', 0);
        $(target).css('paddingTop', 0);
        $(target).css('paddingBottom', 0);
        $(target).css('marginTop', 0);
        $(target).css('marginBottom', 0);
        setTimeout(() => {
            $(target).css('display', 'none');
            $(target).css('height', '');
            $(target).css('padding-top', '');
            $(target).css('padding-bottom', '');
            $(target).css('margin-top', '');
            $(target).css('margin-bottom', '');
            $(target).css('overflow', '');
            $(target).css('transition-duration', '');
            $(target).css('transition-property', '');
        }, duration);

    }

    function slideDown(target, duration= 500) {
        if ($(target).css('display') === 'none') {
            $(target).css('display', 'block');
        }

        $(target).css('overflow', 'hidden');
        $(target).css('height', 0);
        $(target).css('paddingTop', 0);
        $(target).css('paddingBottom', 0);
        $(target).css('marginTop', 0);
        $(target).css('marginBottom', 0);
        $(target).css('boxSizing', 'border-box');
        $(target).css('transitionProperty', 'height, margin, padding');
        $(target).css('transitionDuration', duration + 'ms');
        $(target).css('height', target.offsetHeight + 'px');
        
       $(target).css('padding-top', '');
       $(target).css('padding-bottom', '');
       $(target).css('margin-top', '');
       $(target).css('margin-bottom', '');

	   setTimeout( () => {
           $(target).css('height', '');
           $(target).css('overflow', '');
           $(target).css('transition-duration', '');
           $(target).css('transition-property', '');
		}, duration);
	}
});