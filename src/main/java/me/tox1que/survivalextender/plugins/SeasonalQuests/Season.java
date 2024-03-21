package me.tox1que.survivalextender.plugins.SeasonalQuests;

import me.tox1que.survivalextender.utils.enums.ServerType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Season{
    String from();
    String until();
    ServerType server();
}